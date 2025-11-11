package com.xxxyjade.hiphopghetto.util;

import com.xxxyjade.hiphopghetto.common.enums.SortType;
import org.springframework.stereotype.Component;

@Component
public class KeyGenerator {

    public String generateAlbumInfoKey(Integer id) {
        return "albumInfo::id" + id;
    }

    public String generateAlbumPageKey(Integer page, Integer size, SortType sortType) {
        return "albumPage::page=" + page + "&size=" + size + "&sort=" + sortType;
    }

    /**
     * 生成评论分页查询的缓存键（与 @Cacheable 的 key 规则保持一致）
     * @param sectionId 评论区ID
     * @param page 页码
     * @param size 每页条数
     * @param sortType 排序类型
     * @return 缓存键（如 "commentPage::sectionId=1&page=1&size=10&sort=NEWEST"）
     */
    public String generateCommentPageKey(Long sectionId, Integer page, Integer size, SortType sortType) {
        // 格式：缓存名称::具体参数（与 @Cacheable 的 value 对应，避免和其他缓存冲突）
        return "commentPage::sectionId=" + sectionId + "&page=" + page + "&size=" + size + "&sort=" + sortType;
    }

    public String getAlbumInfoCachePrefix() {
        return "albumInfo";
    }

    public String getAlbumPageCachePrefix() {
        return "albumPage";
    }

    /**
     * 生成评论区缓存的前缀（用于批量删除该评论区的所有缓存）
     * @param sectionId 评论区ID
     * @return 前缀（如 "commentPage::sectionId=1"）
     */
    public String getCommentSectionCachePrefix(Long sectionId) {
        return "commentPage::sectionId=" + sectionId;
    }
}
