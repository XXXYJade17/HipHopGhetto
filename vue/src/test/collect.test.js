import { albumApi } from '@/api/album.js'

// 测试专辑收藏功能
export const testAlbumCollect = async (albumId) => {
  try {
    console.log('测试收藏专辑，ID:', albumId)

    // 调用收藏接口
    const response = await albumApi.toggleCollectAlbum(albumId)
    console.log('收藏接口响应:', response)

    if (response.code === 200) {
      console.log('收藏操作成功')
      return true
    } else {
      console.error('收藏操作失败:', response.message)
      return false
    }
  } catch (error) {
    console.error('收藏操作异常:', error)
    return false
  }
}
