package com.github.hykes.front;

import com.github.hykes.BugReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author hehaiyangwork@gmail.com
 * @date 2019-02-28 20:48:00
 */
@RestController
@RequestMapping("api/report")
public class ReportController {

    @Autowired
    private BugReport bugReport;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer create(@RequestBody Map<String, Object> creator) {
        System.out.println(creator.toString());
        return 1;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void test(@PathVariable Long id) {
        int i = Integer.parseInt("ff");
//        bugReport.report(new HttpClientErrorException(HttpStatus.BAD_GATEWAY));
    }

}
