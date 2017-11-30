
package wad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
/**
 *
 * @author Tomi
 */
@Controller
public class DefaultController {
    
    @GetMapping("/")
    public String handleDefault() {
        return "index";
    }
    
}
