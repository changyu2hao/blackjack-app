package com.example.blackjack.controller;

import com.example.blackjack.game.BlackjackGame;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.blackjack.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.blackjack.service.GameService;
import com.example.blackjack.service.UserService;

import java.math.BigDecimal;

@Controller
public class GameController {

    private final GameService gameService;
    private final UserService userService;
    public GameController(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService=userService;
    }

    @GetMapping("/game")
    public String game(@AuthenticationPrincipal UserDetails userDetails,HttpSession session, Model model) {
        BlackjackGame game = (BlackjackGame) session.getAttribute("game");
        String error = (String) session.getAttribute("gameError");
        User user=userService.getCurrentUser(userDetails);

        model.addAttribute("game", game);
        model.addAttribute("error", error);
        model.addAttribute("balance", user.getBalance());
        session.removeAttribute("gameError");
        return "game";
    }

    @PostMapping("/game/start")
    public String startGame(@RequestParam BigDecimal betAmount,
                            @AuthenticationPrincipal UserDetails userDetails,
                            HttpSession session) {
        BlackjackGame existingGame = (BlackjackGame) session.getAttribute("game");

        try {
            BlackjackGame game = gameService.startGame(betAmount, userDetails, existingGame);
            session.setAttribute("game", game);
        } catch (RuntimeException ex) {
            session.setAttribute("gameError", ex.getMessage());
        }

        return "redirect:/game";
    }
    @PostMapping("/game/hit")
    public String hit(@AuthenticationPrincipal UserDetails userDetails, HttpSession session) {
        BlackjackGame game = (BlackjackGame) session.getAttribute("game");
        gameService.hit(game, userDetails);
        return "redirect:/game";
    }
    @PostMapping("/game/stand")
    public String stand(@AuthenticationPrincipal UserDetails userDetails, HttpSession session) {
        BlackjackGame game = (BlackjackGame) session.getAttribute("game");
        gameService.stand(game, userDetails);
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