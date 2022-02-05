package com.ap.iamstu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@Slf4j
@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties
public class IamstuApplication {

    public static void main(String[] args) {
        SpringApplication.run(IamstuApplication.class, args);

        System.out.println("" +
                "\n" +
                "         -`+yMMMMdMdNMdy.                                                                           \n" +
                "      `oNmNMd+MN:+Mmo``                                                                             \n" +
                "    `omMm-mM:-d/.dd-                                                                                \n" +
                "   .hmyMy -mohhNNMMNhhh/-`                                                                          \n" +
                "  ohMN//h+yNMMMNhhhhhhhhymo:       HIHI                                                                 \n" +
                " :NysMh.yNMMMhys.````````..`                                                                        \n" +
                ".hMNs/:hMMhdMdNMmo.`               `-//                                       `...`                 \n" +
                ":MhmNhhMMhmNo.:+MMmdyo:-        `:+mNMm\n" +
                "-NMdo-sMydNo`  .MMMMMdNh     .-shMMMMMM\n" +
                "-NMmdsyN/Md    .mNMNh`dm+++ydmMMMMMMMMh\n" +
                ":NMy+-yN/MN+    `-oo+sMMMMMMMMMMMMMMd+`\n" +
                "/mMmmhysoMMNy-````dMMMMMMMMNNNNNdds/`  \n" +
                "NNmyyydmoomMMNddddmMNNNMMMMdhh+:`      \n" +
                "::`       `+yNMMNys+:::ssyNNMMMNo.     \n" +
                "             :my`         ``/ohNMd     \n" +
                "            `hm`               .:/     \n" +
                "           `ym:                        \n" +
                "           oMMMy       ");
    }

}
