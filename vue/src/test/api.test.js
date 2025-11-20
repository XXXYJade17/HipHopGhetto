import api from '@/services/api.js'

// 测试全局拦截器功能
export const testGlobalInterceptor = async () => {
  try {
    // 测试正常响应 (200)
    const successResponse = await api.get('/test/success')
    console.log('Success response:', successResponse)
  } catch (error) {
    console.log('Success response error:', error)
  }

  try {
    // 测试未授权响应 (401)
    const authError = await api.get('/test/unauthorized')
    console.log('Auth error response:', authError)
  } catch (error) {
    console.log('Auth error caught:', error)
    if (error.code === 401) {
      console.log('Correctly handled 401 error')
    }
  }

  try {
    // 测试其他错误响应
    const otherError = await api.get('/test/error')
    console.log('Other error response:', otherError)
  } catch (error) {
    console.log('Other error caught:', error)
    if (error.message) {
      console.log('Correctly extracted error message:', error.message)
    }
  }
}
