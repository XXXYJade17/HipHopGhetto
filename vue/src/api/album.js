import request from '@/services/api.js'

// 专辑相关接口
export const albumApi = {
  /**
   * 分页查询专辑列表
   * @param {Object} data - 查询参数
   * @param {number} data.page - 页码（起始页一般为 1）
   * @param {number} data.size - 每页记录数
   * @param {string} data.sortType - 排序类型（可选值：AVG_SCORE、SCORE_COUNT、COLLECT_COUNT、COMMENT_COUNT）
   * @returns {Promise<{code: number, message: string, data: Array}>} 专辑列表
   */
  getAlbums(data) {
    const params = new URLSearchParams();
    if (data.page !== undefined) params.append('page', data.page);
    if (data.size !== undefined) params.append('size', data.size);
    if (data.sortType !== undefined) params.append('sortType', data.sortType);
    return request.get(`/albums?${params.toString()}`);
  },

  /**
   * 根据专辑 ID 查询专辑详细信息（包含统计数据和歌曲列表）
   * @param {number|string} id - 专辑ID
   * @returns {Promise<{code: number, message: string, data: Object}>} 专辑详情
   */
  getAlbumDetail(id) {
    console.log('调用 getAlbumDetail，传入的ID:', id, '类型:', typeof id)
    const url = `/album/${id}`
    console.log('构造的URL:', url)
    return request.get(url)
  },

  /**
   * 查询当前登录用户对指定专辑的评分记录
   * @param {number|string} id - 专辑ID
   * @returns {Promise<{code: number, message: string, data: number}>} 用户评分数据
   */
  getUserAlbumScore(id) {
    console.log('调用 getUserAlbumScore，传入的ID:', id, '类型:', typeof id)
    const url = `/rating/${id}`
    console.log('构造的URL:', url)
    return request.get(url)
  },

  /**
   * 提交专辑评分
   * @param {Object} data - 评分数据
   * @param {number|string} data.targetId - 专辑ID
   * @param {number} data.score - 评分（1-10分）
   * @returns {Promise<{code: number, message: string, data: null}>} 评分结果
   */
  submitAlbumScore(data) {
    console.log('调用 submitAlbumScore，传入的数据:', data)
    return request.post('/rating', { ...data, targetType: 1 })
  },

  /**
   * 收藏专辑
   * @param {number|string} id - 专辑ID
   * @returns {Promise<{code: number, message: string, data: null}>} 收藏结果
   */
  collectAlbum(id) {
    console.log('调用 collectAlbum，传入的ID:', id, '类型:', typeof id)
    const url = `/collection/${id}?targetType=1`
    console.log('构造的URL:', url)
    return request.post(url)
  },

  /**
   * 取消收藏专辑
   * @param {number|string} id - 专辑ID
   * @returns {Promise<{code: number, message: string, data: null}>} 取消收藏结果
   */
  uncollectAlbum(id) {
    console.log('调用 uncollectAlbum，传入的ID:', id, '类型:', typeof id)
    const url = `/collection/${id}?targetType=1`
    console.log('构造的URL:', url)
    return request.delete(url)
  },

  /**
   * 查询专辑收藏状态
   * @param {number|string} id - 专辑ID
   * @returns {Promise<{code: number, message: string, data: boolean}>} 收藏状态
   */
  getAlbumCollectStatus(id) {
    console.log('调用 getAlbumCollectStatus，传入的ID:', id, '类型:', typeof id)
    const url = `/collection/${id}`
    console.log('构造的URL:', url)
    return request.get(url)
  },

  /**
   * 分页查询专辑评论列表
   * @param {Object} data - 查询参数
   * @param {string} data.sortType - 排序类型（可选值：DEFAULT）
   * @param {number} data.page - 页码（起始页一般为 1）
   * @param {number} data.size - 每页记录数
   * @param {number|string} data.commentSectionId - 评论区ID
   * @returns {Promise<{code: number, message: string, data: Object}>} 评论列表和分页信息
   */
  getAlbumComments(data) {
    return request.post('/api/comments', data)
  },

  /**
   * 创建专辑评论
   * @param {Object} data - 评论数据
   * @param {number|string} data.commentSectionId - 评论区ID
   * @param {string} data.content - 评论内容
   * @returns {Promise<{code: number, message: string, data: Object}>} 创建的评论
   */
  createAlbumComment(data) {
    return request.post('/api/comment', data)
  },
}
