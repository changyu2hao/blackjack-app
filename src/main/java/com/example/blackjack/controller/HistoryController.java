package com.example.blackjack.controller;

import com.example.blackjack.entity.GameHistory;
import com.example.blackjack.service.HistoryService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HistoryController {
    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/history")
    public String history(@AuthenticationPrincipal UserDetails userDetails,
                          @RequestParam(defaultValue = "0") int page,
                          Model model){
        int currentPage = Math.max(page, 0);

        Page<GameHistory> historyPage = historyService.getHistoryForUser(userDetails, currentPage);

        model.addAttribute("histories", historyPage.getContent());
        model.addAttribute("historyPage", historyPage);
        model.addAttribute("currentPage", currentPage);
        return "history";
    }

}
