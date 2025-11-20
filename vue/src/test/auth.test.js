import { albumApi } from '@/api/album.js'

// 测试401错误处理
export const testAuthErrorHandling = async () => {
  try {
    console.log('测试获取专辑详情...')
    // 清除认证信息以模拟401错误
    localStorage.removeItem('authentication')

    // 尝试获取专辑详情
    const response = await albumApi.getAlbumDetail(98339170)
    console.log('专辑详情获取成功:', response)
    return true
  } catch (error) {
    console.log('捕获到错误:', error)

    // 检查是否正确处理了401错误
    if (error.code === 401) {
      console.log('正确处理了401错误')
      return true
    } else {
      console.error('未正确处理401错误:', error)
      return false
    }
  }
}
