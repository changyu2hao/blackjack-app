package com.example.blackjack.controller;

import com.example.blackjack.entity.User;
import com.example.blackjack.game.BlackjackGame;
import com.example.blackjack.service.GameService;
import com.example.blackjack.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
@Validated
public class GameController {

    private final GameService gameService;
    private final UserService userService;

    public GameController(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
    }

    @GetMapping("/game")
    public String game(@AuthenticationPrincipal UserDetails userDetails,
                       HttpSession session,
                       Model model) {
        BlackjackGame game = (BlackjackGame) session.getAttribute("game");
        String error = (String) session.getAttribute("gameError");
        User user = userService.getCurrentUser(userDetails);

        model.addAttribute("game", game);
        model.addAttribute("error", error);
        model.addAttribute("balance", user.getBalance());

        session.removeAttribute("gameError");
        return "game";
    }

    @PostMapping("/game/start")
    public String startGame(
            @RequestParam
            @NotNull
            @DecimalMin("1.00")
            @Digits(integer = 10, fraction = 2)
            BigDecimal betAmount,
            @AuthenticationPrincipal UserDetails userDetails,
            HttpSession session) {

        BlackjackGame existingGame = (BlackjackGame) session.getAttribute("game");

        try {
            BlackjackGame game = gameService.startGame(
                    betAmount,
                    userDetails,
                    existingGame
            );
            session.setAttribute("game", game);
        } catch (RuntimeException ex) {
            session.setAttribute("gameError", ex.getMessage());
        }

        return "redirect:/game";
    }

    @PostMapping("/game/hit")
    public String hit(@AuthenticationPrincipal UserDetails userDetails,
                      HttpSession session) {
        BlackjackGame game = (BlackjackGame) session.getAttribute("game");
        gameService.hit(game, userDetails);
        return "redirect:/game";
    }

    @PostMapping("/game/stand")
    public String stand(@AuthenticationPrincipal UserDetails userDetails,
                        HttpSession session) {
        BlackjackGame game = (BlackjackGame) session.getAttribute("game");
        gameService.stand(game, userDetails);
        return "redirect:/game";
    }

    @PostMapping("/game/split")
    public String split(@AuthenticationPrincipal UserDetails userDetails,
                        HttpSession session) {
        BlackjackGame game = (BlackjackGame) session.getAttribute("game");

        try {
            gameService.split(game, userDetails);
        } catch (RuntimeException ex) {
            session.setAttribute("gameError", ex.getMessage());
        }

        return "redirect:/game";
    }

    @PostMapping("/game/double-down")
    public String doubleDown(@AuthenticationPrincipal UserDetails userDetails,
                             HttpSession session) {
        BlackjackGame game = (BlackjackGame) session.getAttribute("game");

        try {
            gameService.doubleDown(game, userDetails);
        } catch (RuntimeException ex) {
            session.setAttribute("gameError", ex.getMessage());
        }

        return "redirect:/game";
    }

    @PostMapping("/game/new")
    public String newGame(HttpSession session) {
        BlackjackGame game = (BlackjackGame) session.getAttribute("game");

        try {
            gameService.validateNewGame(game);
            session.removeAttribute("game");
        } catch (RuntimeException ex) {
            session.setAttribute("gameError", ex.getMessage());
        }

        return "redirect:/game";
    }
}
