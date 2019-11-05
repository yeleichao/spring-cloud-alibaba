package com.ylc.contentcenter.test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @Description: TODO
 * @Date: 2019/9/28
 */
public class SteamTest {

    public static void main(String[] args) {
        List<Menu> list = new ArrayList<>();
        for(int i=0; i<10000000; i++){
            Menu menu = Menu.builder()
                    .calories(i)
                    .name("名称："+i)
                    .build();
            list.add(menu);
        }
        Long start = System.currentTimeMillis();
        List<String> strings = list.stream().filter(d->d.getCalories() < 400)
                .sorted(Comparator.comparing(Menu::getCalories))
                .map(Menu::getName)
                .collect(toList());
        System.out.println(strings.toString());
        System.out.println(System.currentTimeMillis()-start);

    }
}
