package fun.jwei.community.controller;


import fun.jwei.community.util.GenVcode;
import fun.jwei.community.util.MyConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LoginController {

    private GenVcode genVcode;


    @Autowired
    public void setGenVcode(GenVcode genVcode) {
        this.genVcode = genVcode;
    }


    @ResponseBody
    @GetMapping("/vcode")
    public void genvcode(HttpServletRequest request,
                         HttpServletResponse response,
                         String t) throws IOException {

        HttpSession session = request.getSession();
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a png
        response.setContentType("image/png");

        String vcode = genVcode.generateVCode(response.getOutputStream());

        session.setAttribute(MyConstants.VALIDATE_KEY, vcode);
    }
}
