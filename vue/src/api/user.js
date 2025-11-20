import request from '@/services/api.js'

// 用户相关接口
export const userApi = {
  /**
   * 用户注册
   * @param {Object} data - 注册数据
   * @param {string} data.username - 用户名
   * @param {string} data.phone - 手机号（可选）
   * @param {string} data.email - 邮箱（可选，手机号和邮箱至少填一个）
   * @param {string} data.password - 密码
   * @param {string} data.confirmPassword - 确认密码
   * @returns {Promise<{code: number, message: string, data: Object}>} 注册结果
   */
  register(data) {
    return request.post('/user/register', data)
  },

  /**
   * 用户登录
   * @param {Object} data - 登录数据
   * @param {string} data.account - 登录账号（用户名/手机号/邮箱）
   * @param {string} data.password - 登录密码
   * @returns {Promise<{code: number, message: string, data: Object}>} 登录结果
   */
  login(data) {
    return request.post('/user/login', data)
  },

  /**
   * 获取指定用户信息
   * @param {string|number} id - 用户ID
   * @returns {Promise<{code: number, message: string, data: Object}>} 用户信息
   */
  getUserProfile(id) {
    return request.get(`/user/${id}`)
  },

  /**
   * 更新当前用户信息
   * @param {Object} data - 用户信息
   * @param {string} data.username - 用户名
   * @param {number} data.sex - 性别（0:保密,1:男,2:女）
   * @param {string} data.avatar - 头像URL
   * @param {string} data.description - 个人简介
   * @param {string} data.birthday - 生日
   * @returns {Promise<{code: number, message: string, data: Object}>} 更新结果
   */
  updateUserProfile(data) {
    return request.put('/user/profile', data)
  },

  /**
   * 修改密码
   * @param {Object} data - 密码数据
   * @param {string} data.currentPassword - 当前密码
   * @param {string} data.newPassword - 新密码
   * @returns {Promise<{code: number, message: string, data: Object}>} 修改结果
   */
  changePassword(data) {
    return request.put('/user/password', data)
  },
}
