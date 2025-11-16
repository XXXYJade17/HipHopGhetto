package com.xxxyjade.hiphopghetto.util;

import com.xxxyjade.hiphopghetto.common.pojo.entity.Song;
import com.xxxyjade.hiphopghetto.mapper.SongMapper;
import com.xxxyjade.hiphopghetto.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.time.LocalDate;
import java.util.List;

@Component
@Slf4j
public class NetEaseSongCrawlUtil implements PageProcessor {

    @Autowired
    private SongService songService;

    private static final String SONG_URL = "https://music.163.com/song?id=";
    private static final Site site = Site.me()
            .setDomain("music.163.com")        // 目标域名
            .setSleepTime(100)               // 爬取间隔（避免反爬）
            .setRetryTimes(3)                 // 重试次数
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"); // 模拟浏览器UA

    private Long albumId;
    private LocalDate releaseTime;

    @Override
    public Site getSite() {
        return site;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void process(Page page) {
        // 判断 URL
        if (page.getUrl().regex("https?://music\\.163\\.com/song\\?id=\\d+").match()) {
            Selectable html = page.getHtml();
            // 歌曲 id
            Long neteaseId = Long.parseLong(page.getUrl().regex("id=(\\d+)").get());
            // 歌曲名
            String songName = html.xpath("//meta[@property='og:title']/@content").get();
            // 专辑名
            String albumName = html.xpath("//p[contains(@class, 'des') and contains(@class, 's-fc4')]/a[starts-with(@href, '/album?id=')]/text()").get();
            // 歌手
            String artists = String.join(" / ", html.xpath("//p[@class='des s-fc4']/span/a/text()").all());
            // 时长（秒）
            Integer duration = Integer.parseInt(html.xpath("//meta[@property='music:duration']/@content").get());
            // 封面 URL
            String coverUrl = html.xpath("//meta[@property='og:image']/@content").get();

            Song song = Song.builder()
                    .neteaseId(neteaseId)
                    .songName(songName)
                    .albumId(albumId)
                    .albumName(albumName)
                    .artists(artists)
                    .releaseTime(releaseTime)
                    .duration(duration)
                    .coverUrl(coverUrl)
                    .build();
            // 插入数据
            songService.insertIgnore(song);
        }
    }

    public void startCrawl(List<Long> ids, Long albumId, LocalDate releaseTime) {
        this.albumId = albumId;
        this.releaseTime = releaseTime;

        Spider spider = Spider.create(this);
        ids.forEach(id -> spider.addUrl(SONG_URL + id));
        spider.thread(1)
                .run();
    }

}
