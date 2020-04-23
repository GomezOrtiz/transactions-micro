package com.gomezortiz.transactionsmicro.shared.docs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApiDocsController {

    @GetMapping("/api/docs")
    public String getDocs() {
        return "redirect:/swagger-ui.html";
    }
}
