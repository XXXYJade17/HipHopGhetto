import { ref } from 'vue'
import axios from 'axios'

// 创建axios实例
const api = axios.create({
  baseURL: '/api', // 使用代理路径而不是直接访问后端
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
})

// 请求拦截器
api.interceptors.request.use(
  (config) => {
    // 获取token
    const token = localStorage.getItem('authentication')
    if (token) {
      config.headers['authentication'] = token
    }

    // 打印请求信息用于调试
    console.log('发出请求:', config.method?.toUpperCase(), config.url)
    if (config.data) {
      console.log('请求数据:', config.data)
    }

    return config
  },
  (error) => {
    return Promise.reject(error)
  },
)

// 响应拦截器
api.interceptors.response.use(
  (response) => {
    // 打印响应信息用于调试
    console.log(
      '收到响应:',
      response.config.method?.toUpperCase(),
      response.config.url,
      response.status,
    )
    console.log('响应数据:', response.data)

    // 对响应数据做点什么
    // 后端统一返回Result对象，包含code、message和data字段
    const { code, message, data } = response.data

    // 判断状态码并处理
    if (code === 200) {
      // 成功响应，直接返回数据
      return response.data
    } else if (code === 401) {
      // 未授权，清除认证信息并提示
      localStorage.removeItem('authentication')
      // 创建一个带有code属性的错误对象
      const error = new Error(message || '未授权访问')
      error.code = code
      error.data = data
      return Promise.reject(error)
    } else {
      // 其他错误状态码，提取msg信息
      const error = new Error(message || '请求失败')
      error.code = code
      error.data = data
      return Promise.reject(error)
    }
  },
  (error) => {
    // HTTP错误处理 (如网络错误、服务器错误等)
    console.error('网络请求错误:', error)
    // 检查是否有响应以及响应状态码
    if (error.response && error.response.status === 401) {
      // HTTP 401错误
      localStorage.removeItem('authentication')
      const authError = new Error('未授权访问')
      authError.code = 401
      // 添加额外的信息帮助前端识别
      authError.response = error.response
      return Promise.reject(authError)
    }
    return Promise.reject(error)
  },
)

export default api

// 创建一个响应式变量来存储token
export const useAuthStore = () => {
  const token = ref(localStorage.getItem('authentication') || null)

  // 设置token
  const setToken = (newToken) => {
    token.value = newToken
    localStorage.setItem('authentication', newToken)
  }

  // 清除token
  const clearToken = () => {
    token.value = null
    localStorage.removeItem('authentication')
  }

  return {
    token,
    setToken,
    clearToken,
  }
}

// 创建一个API客户端
export const apiClient = {
  // 基础URL
  baseURL: '/api',

  // 发送请求的通用方法
  async request(url, options = {}) {
    // 添加认证头
    const headers = {
      ...options.headers,
      'Content-Type': 'application/json',
    }

    if (useAuthStore().token.value) {
      headers['authentication'] = useAuthStore().token.value
    }

    const response = await fetch(this.baseURL + url, {
      ...options,
      headers,
    })

    const data = await response.json()

    // 处理错误
    if (!response.ok) {
      throw new Error(data.message || '请求失败')
    }

    return data
  },

  // 注册方法
  async register(userData) {
    return this.request('/user/register', {
      method: 'POST',
      body: JSON.stringify(userData),
    })
  },

  // 登录方法
  async login(credentials) {
    return this.request('/user/login', {
      method: 'POST',
      body: JSON.stringify(credentials),
    })
  },
}
