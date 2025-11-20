import { userApi } from '@/api'

// 用户注册
export const register = async (userData) => {
  try {
    const response = await userApi.register(userData)

    // 根据接口文档，状态码为200才是成功
    // 后端返回统一的Result对象：{code: number, message: string, data: Object}
    if (response.code === 200) {
      return response
    } else {
      throw new Error(response.message || '注册失败')
    }
  } catch (error) {
    // 如果是网络错误或其他异常
    if (error.message) {
      throw error
    }
    // 否则抛出通用错误信息
    throw new Error('注册失败，请检查网络连接')
  }
}

// 用户登录
export const login = async (credentials) => {
  try {
    const response = await userApi.login(credentials)

    // 根据接口文档，状态码为200才是成功
    // 后端返回统一的Result对象：{code: number, message: string, data: Object}
    if (response.code === 200) {
      return response
    } else {
      throw new Error(response.message || '登录失败')
    }
  } catch (error) {
    // 如果是网络错误或其他异常
    if (error.message) {
      throw error
    }
    // 否则抛出通用错误信息
    throw new Error('登录失败，请检查网络连接')
  }
}
