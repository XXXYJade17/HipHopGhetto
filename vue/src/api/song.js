import request from '@/services/api.js'

// 歌曲相关接口
export const songApi = {
  /**
   * 分页查询歌曲列表
   * @param {Object} data - 查询参数
   * @param {number} data.page - 页码（起始页一般为 1）
   * @param {number} data.size - 每页记录数
   * @param {string} data.sortType - 排序类型（可选值：默认、PLAY_COUNT、FAVORITE_COUNT、COMMENT_COUNT）
   * @returns {Promise<{code: number, message: string, data: Array}>} 歌曲列表
   */
  getSongs(data) {
    console.log('调用歌曲接口，发送数据:', data)
    const params = new URLSearchParams();
    if (data.page !== undefined) params.append('page', data.page);
    if (data.size !== undefined) params.append('size', data.size);
    if (data.sortType !== undefined) params.append('sortType', data.sortType);
    return request.get(`/songs?${params.toString()}`);
  },

  /**
   * 根据歌曲 ID 查询歌曲详细信息
   * @param {string} id - 歌曲ID
   * @returns {Promise<{code: number, message: string, data: Object}>} 歌曲详情
   */
  getSongDetail(id) {
    return request.get(`/song/${id}`)
  },

  /**
   * 提交歌曲评分
   * @param {Object} data - 评分数据
   * @param {string} data.targetId - 歌曲ID
   * @param {number} data.score - 评分（1-10分）
   * @returns {Promise<{code: number, message: string, data: Object}>} 评分结果
   */
  submitSongScore(data) {
    return request.post('/rating', { ...data, targetType: 2 })
  },

  /**
   * 获取用户对歌曲的评分
   * @param {string} id - 歌曲ID
   * @returns {Promise<{code: number, message: string, data: number}>} 用户评分，0表示未评分
   */
  getUserSongScore(id) {
    return request.get(`/rating/${id}`)
  },

  /**
   * 收藏歌曲
   * @param {string} id - 歌曲ID
   * @returns {Promise<{code: number, message: string, data: null}>} 收藏结果
   */
  collectSong(id) {
    const url = `/collection/${id}?targetType=2`
    return request.post(url)
  },

  /**
   * 取消收藏歌曲
   * @param {string} id - 歌曲ID
   * @returns {Promise<{code: number, message: string, data: null}>} 取消收藏结果
   */
  uncollectSong(id) {
    const url = `/collection/${id}?targetType=2`
    return request.delete(url)
  },
  
  /**
   * 查询歌曲收藏状态
   * @param {string} id - 歌曲ID
   * @returns {Promise<{code: number, message: string, data: boolean}>} 收藏状态
   */
  getSongCollectionStatus(id) {
    const url = `/collection/${id}`
    return request.get(url)
  },

  /**
   * 分页查询歌曲评论列表
   * @param {Object} data - 查询参数
   * @param {string} data.sortType - 排序类型（可选值：DEFAULT）
   * @param {number} data.page - 页码（起始页一般为 1）
   * @param {number} data.size - 每页记录数
   * @param {number|string} data.commentSectionId - 评论区ID
   * @returns {Promise<{code: number, message: string, data: Object}>} 评论列表和分页信息
   */
  getSongComments(data) {
    return request.post('/api/comments', data)
  },

  /**
   * 创建歌曲评论
   * @param {Object} data - 评论数据
   * @param {number|string} data.commentSectionId - 评论区ID
   * @param {string} data.content - 评论内容
   * @returns {Promise<{code: number, message: string, data: Object}>} 创建的评论
   */
  createSongComment(data) {
    return request.post('/api/comment', data)
  },
}
