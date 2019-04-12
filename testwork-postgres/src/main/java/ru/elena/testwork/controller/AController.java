package ru.elena.testwork.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Predicate;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingByConcurrent;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.elena.testwork.domain.User;
import ru.elena.testwork.service.UserService;

@Controller
public class AController {

    public static final int ZERO = 0;
    public static final Set<String> SET = new HashSet<>(Arrays.asList("checked",
            "success", "change-data", "pay", "confirmBooking", "confirmRecord", "after", "sent", "send", "add", "done"));
    private final UserService userService;

    @Autowired
    public AController(UserService userService) {
        this.userService = userService;
    }

    public AController() {
        this.userService = null;
    }
    

    @GetMapping("/")
    public String aPage(Model model) {
        return "apage";
    }

    @GetMapping("/find")
    public String findPage(@RequestParam("data") String data, Model model) {
        switch (data) {
            case "forms":
                return "forward:/forms";
            case "steps":
                return "forward:/steps";
            case "top":
                return "forward:/top";
            default:
                return "error";
        }

    }

    @GetMapping("/forms")
    public String formsPage(Model model) {
        List<User> users = (List<User>) userService.findAll();
        Instant max = users.stream().map(user -> Instant.ofEpochSecond(Long.parseLong(user.getTs()))).max(Comparator.naturalOrder()).orElse(null);
        if (max == null) {
            return "error";
        }
        String limit = String.valueOf(max.minus(Duration.ofHours(1)).getEpochSecond());
        ConcurrentMap<String, String> map = users.parallelStream()
                .filter(user -> {
                    return user.getTs().compareTo(limit) >= ZERO;
                }).collect(groupingByConcurrent(User::getSsoid, mapping(User::getFormid, joining(", "))));
        model.addAttribute("map", map);
        return "forms";
    }

    @GetMapping("/steps")
    public String stepsPage(Model model) {
        final Predicate<String> done = s -> !SET.contains(s);
        List<User> users = (List<User>) userService.findAll();
        ConcurrentMap<String, String> map = users.parallelStream().filter(user -> done.test(user.getSubtype())).collect(groupingByConcurrent(User::getSsoid, mapping(User::getSubtype, joining(", "))));
        model.addAttribute("map", map);
        return "steps";
    }

    @GetMapping("/top")
    public String topPage(Model model) {
        List<User> users = (List<User>) userService.findAll();
        ConcurrentMap<String, Long> map = users.parallelStream().collect(groupingByConcurrent(User::getFormid, counting()));
        long minValue = map.values().stream().sorted(Comparator.reverseOrder()).limit(5).min(Comparator.naturalOrder()).orElse(new Long(0));
        map.forEach((k, v) -> {
            if (v < minValue) {
                map.remove(k);
            }
        });

        model.addAttribute("map", map);
        return "top";
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(Exception ex) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex.getMessage());
        mav.setViewName("error");
        return mav;
    }

}
