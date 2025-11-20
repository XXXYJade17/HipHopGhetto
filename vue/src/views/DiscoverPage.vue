<template>
  <div class="container py-8 pt-0">
    <!-- 二级导航栏（内容类型和排序方式） -->
    <div
      class="secondary-nav sticky top-20 z-40 bg-[#121212] border-b border-[rgba(255,255,255,0.1)]"
    >
      <div class="container">
        <div class="flex justify-between items-center">
          <ul class="flex space-x-8">
            <li>
              <a href="#" class="block py-4 text-[#FFD700] font-medium relative" data-type="album"
                >专辑</a
              >
            </li>
            <li>
              <a
                href="#"
                class="block py-4 text-[#CCCCCC] hover:text-white font-medium"
                data-type="song"
                >歌曲</a
              >
            </li>
            <li>
              <a
                href="#"
                class="block py-4 text-[#CCCCCC] hover:text-white font-medium"
                data-type="artist"
                >歌手</a
              >
            </li>
          </ul>

          <!-- 排序方式 -->
          <div class="flex space-x-6">
            <a
              href="#"
              class="text-[#FFD700] font-medium border-b-2 border-[#FFD700] pb-3 pt-3"
              data-sort="default"
              >默认</a
            >
            <a
              href="#"
              class="text-[#CCCCCC] hover:text-white font-medium pb-3 pt-3"
              data-sort="rating"
              >最高评分</a
            >
            <a
              href="#"
              class="text-[#CCCCCC] hover:text-white font-medium pb-3 pt-3"
              data-sort="favorites"
              >最多收藏</a
            >
            <a
              href="#"
              class="text-[#CCCCCC] hover:text-white font-medium pb-3 pt-3"
              data-sort="comments"
              >最多评论</a
            >
          </div>
        </div>
      </div>
    </div>

    <!-- 内容展示区 -->
    <section class="py-8">
      <div class="container">
        <!-- 加载状态 -->
        <div v-show="loading" id="loading" class="flex justify-center items-center py-16">
          <div
            class="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-[#FFD700]"
          ></div>
        </div>

        <!-- 错误状态 -->
        <div v-show="error" id="error" class="text-center py-16">
          <p class="text-[#FF6B6B]">{{ error }}</p>
          <button @click="retry" class="mt-4 px-6 py-2 main-btn">重试</button>
        </div>

        <!-- 专辑网格 -->
        <div
          v-show="contentType === 'album' && !loading && !error"
          id="albumContainer"
          class="content-grid"
        >
          <div v-if="albums.length === 0" class="text-center py-8 text-gray-400 col-span-full">
            暂无数据
          </div>
          <!-- 专辑卡片将通过JavaScript动态生成 -->
          <div
            v-for="album in albums"
            :key="album.id"
            class="content-card hover-float"
            @click="goToAlbumDetail(album.id)"
          >
            <div class="content-cover mb-4">
              <img
                alt="Album cover"
                class="w-full aspect-square object-cover transition-transform duration-500"
                :src="album.coverUrl"
              />
              <div class="play-button">
                <iconify-icon icon="mdi:play" width="24"></iconify-icon>
              </div>
              <div class="rating-badge">
                {{ album.avgScore ? album.avgScore + '分' : '暂无评分' }}
              </div>
            </div>
            <h4 class="text-white font-bold truncate">{{ album.albumName }}</h4>
            <p class="text-sm text-[#808080] mt-1">
              <span class="artist-name">{{ album.artists }}</span>
            </p>
            <p class="text-xs text-[#666] mt-1">
              {{ formatDate(album.releaseTime) }}
            </p>
          </div>
        </div>

        <!-- 歌曲网格 -->
        <div
          v-show="contentType === 'song' && !loading && !error"
          id="songContainer"
          class="content-grid"
        >
          <div v-if="songs.length === 0" class="text-center py-8 text-gray-400 col-span-full">
            暂无歌曲数据
          </div>
          <div
            v-else
            v-for="song in songs"
            :key="song.id"
            class="content-card hover-float"
            @click="goToSongDetail(song.id)"
          >
            <div class="content-cover mb-4">
              <img
                alt="Song cover"
                class="w-full aspect-square object-cover transition-transform duration-500"
                :src="song.coverUrl || 'https://placehold.co/300x300/1a1a1a/ffd700?text=Song'"
              />
              <div class="play-button">
                <iconify-icon icon="mdi:play" width="24"></iconify-icon>
              </div>
              <div class="rating-badge">
                {{ song.avgScore ? song.avgScore.toFixed(1) + '分' : '暂无评分' }}
              </div>
            </div>
            <h4 class="text-white font-bold truncate">{{ song.songName }}</h4>
            <p class="text-sm text-[#808080] mt-1">
              <span class="artist-name">{{ song.artists }}</span>
            </p>
          </div>
        </div>

        <!-- 分页控件 -->
        <div v-show="shouldShowPagination" id="pagination" class="pagination">
          <button
            id="prevPage"
            class="pagination-btn"
            :disabled="currentPage === 1"
            @click="goToPage(currentPage - 1)"
          >
            <iconify-icon icon="mdi:chevron-left" width="16"></iconify-icon>
          </button>

          <div id="pageNumbers" class="flex">
            <button
              v-for="page in pageNumbers"
              :key="page"
              class="pagination-btn"
              :class="{ active: currentPage === page }"
              @click="goToPage(page)"
            >
              {{ page }}
            </button>
          </div>

          <button
            id="nextPage"
            class="pagination-btn"
            :disabled="!hasNextPage"
            @click="goToPage(currentPage + 1)"
          >
            <iconify-icon icon="mdi:chevron-right" width="16"></iconify-icon>
          </button>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { albumApi, songApi } from '@/api'

// 专辑数据
const albums = ref([])

// 歌曲数据
const songs = ref([])

// 当前页码
const currentPage = ref(1)

// 每页大小
const pageSize = ref(20) // 修改为20

// 总页数
const totalPages = ref(1)

// 是否有下一页
const hasNextPage = ref(false)

// 当前内容类型 (album, song, artist)
const contentType = ref('album')

// 排序类型映射
const sortTypeMap = {
  default: 'DEFAULT',
  rating: 'AVG_SCORE',
  favorites: 'COLLECT_COUNT',
  comments: 'COMMENT_COUNT',
}

// 歌曲排序类型映射
const songSortTypeMap = {
  default: 'DEFAULT',
  rating: 'AVG_SCORE',
  favorites: 'COLLECT_COUNT',
  comments: 'COMMENT_COUNT',
}

// 加载状态
const loading = ref(false)

// 错误信息
const error = ref(null)

// 路由实例
const router = useRouter()

// 计算是否应该显示分页组件
const shouldShowPagination = computed(() => {
  // 显示分页的条件：
  // 1. 不在加载状态且没有错误
  // 2. 有数据
  // 3. 有下一页或者总页数大于1
  if (contentType.value === 'album') {
    return (
      !loading.value &&
      !error.value &&
      albums.value.length > 0 &&
      (hasNextPage.value || totalPages.value > 1)
    )
  } else if (contentType.value === 'song') {
    return (
      !loading.value &&
      !error.value &&
      songs.value.length > 0 &&
      (hasNextPage.value || totalPages.value > 1)
    )
  }
  return false
})

// 计算显示的页码
const pageNumbers = computed(() => {
  const pages = []

  // 如果总页数为1且没有下一页，则不显示分页按钮
  if (totalPages.value === 1 && !hasNextPage.value) {
    return pages
  }

  if (totalPages.value <= 3) {
    // 如果总页数小于等于3，显示所有页码
    for (let i = 1; i <= totalPages.value; i++) {
      pages.push(i)
    }
  } else {
    // 总页数大于3的情况
    if (currentPage.value === 1) {
      // 第一页：显示1、2
      pages.push(1, 2)
    } else if (currentPage.value === totalPages.value) {
      // 最后一页：显示倒数第二页、最后一页
      pages.push(totalPages.value - 1, totalPages.value)
    } else {
      // 中间页：显示前一页、当前页、后一页
      pages.push(currentPage.value - 1, currentPage.value, currentPage.value + 1)
    }
  }

  return pages
})

// 格式化日期显示
const formatDate = (dateString) => {
  if (!dateString) return '未知日期'

  const date = new Date(dateString)
  if (isNaN(date.getTime())) return '未知日期'

  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  return `${year}-${month}-${day}`
}

// 获取当前选中的排序类型
const getActiveSortType = () => {
  const activeSortLink = document.querySelector('.secondary-nav a[data-sort].text-\\[\\#FFD700\\]')
  return activeSortLink ? activeSortLink.getAttribute('data-sort') : 'default'
}

// 跳转到指定页码
const goToPage = (page) => {
  if (page < 1 || page > totalPages.value) return
  currentPage.value = page
  const sortType = getActiveSortType()
  if (contentType.value === 'album') {
    fetchAlbums(sortType)
  } else if (contentType.value === 'song') {
    fetchSongs(sortType)
  }
}

// 获取专辑数据
const fetchAlbums = async (sortType = 'default') => {
  try {
    console.log('开始获取专辑数据，排序类型:', sortType, '页码:', currentPage.value)
    // 显示加载状态
    loading.value = true
    error.value = null

    const response = await albumApi.getAlbums({
      page: currentPage.value,
      size: pageSize.value + 1, // 多查询一个以判断是否有下一页
      sortType: sortTypeMap[sortType] || undefined,
    })

    console.log('专辑数据响应:', response)

    if (response.code === 200) {
      const result = response.data
      const data = result.data || []

      // 更新总数量和总页数
      const total = result.total || 0
      totalPages.value = Math.ceil(total / pageSize.value)

      // 判断是否有下一页
      hasNextPage.value = data.length > pageSize.value
      // 实际显示的数据只取前pageSize个
      albums.value = hasNextPage.value ? data.slice(0, pageSize.value) : data

      console.log('获取到专辑数量:', albums.value.length)
    } else {
      throw new Error(response.message || '获取专辑数据失败')
    }
  } catch (error) {
    console.error('获取专辑数据失败:', error)
    error.value = '获取专辑数据失败: ' + (error.message || '未知错误')
  } finally {
    loading.value = false
  }
}

// 获取歌曲数据
const fetchSongs = async (sortType = 'default') => {
  try {
    console.log('开始获取歌曲数据，排序类型:', sortType, '页码:', currentPage.value)
    // 显示加载状态
    loading.value = true
    error.value = null
    songs.value = [] // 清空之前的歌曲数据

    const requestData = {
      page: currentPage.value,
      size: pageSize.value + 1, // 多查询一个以判断是否有下一页
      sortType: songSortTypeMap[sortType] || undefined,
    }

    console.log('发送歌曲请求数据:', requestData)

    const response = await songApi.getSongs(requestData)

    console.log('歌曲数据响应:', response)

    if (response.code === 200) {
      const result = response.data
      const data = result.data || []

      // 更新总数量和总页数
      const total = parseInt(result.total) || 0
      totalPages.value = Math.ceil(total / pageSize.value)

      // 判断是否有下一页
      hasNextPage.value = data.length > pageSize.value
      // 实际显示的数据只取前pageSize个
      songs.value = hasNextPage.value ? data.slice(0, pageSize.value) : data

      console.log('获取到的歌曲数量:', songs.value.length)
    } else {
      throw new Error(response.message || '获取歌曲数据失败')
    }
  } catch (error) {
    console.error('获取歌曲数据失败:', error)
    error.value = '获取歌曲数据失败: ' + (error.message || '未知错误')
    songs.value = [] // 出错时清空歌曲数据
  } finally {
    loading.value = false
  }
}

// 跳转到专辑详情页
const goToAlbumDetail = (albumId) => {
  console.log('跳转到专辑详情页，ID:', albumId, '类型:', typeof albumId)
  router.push(`/album/${albumId}`)
}

// 跳转到歌曲详情页
const goToSongDetail = (songId) => {
  console.log('跳转到歌曲详情页，ID:', songId, '类型:', typeof songId)
  router.push(`/song/${songId}`)
}

// 重试
const retry = () => {
  console.log('重试，当前内容类型:', contentType.value)
  if (contentType.value === 'album') {
    const sortType = getActiveSortType()
    fetchAlbums(sortType)
  } else if (contentType.value === 'song') {
    const sortType = getActiveSortType()
    fetchSongs(sortType)
  }
}

onMounted(() => {
  console.log('DiscoverPage组件已挂载')
  // 获取二级导航链接
  const secondaryNavLinks = document.querySelectorAll('.secondary-nav a[data-type]')

  // 添加二级导航点击事件监听器
  secondaryNavLinks.forEach((link) => {
    link.addEventListener('click', function (e) {
      e.preventDefault()

      console.log('点击了导航链接:', this.getAttribute('data-type'))

      // 移除所有链接的活动状态
      secondaryNavLinks.forEach((l) => {
        l.classList.remove('text-[#FFD700]')
        l.classList.add('text-[#CCCCCC]')
      })

      // 为当前点击的链接添加活动状态
      this.classList.remove('text-[#CCCCCC]')
      this.classList.add('text-[#FFD700]')

      // 根据点击的链接显示相应的内容
      const type = this.getAttribute('data-type')
      contentType.value = type
      // 重置页码
      currentPage.value = 1
      totalPages.value = 1
      console.log('切换到内容类型:', type)

      // 根据内容类型加载数据
      if (type === 'album') {
        const sortType = getActiveSortType()
        console.log('获取专辑数据，排序类型:', sortType)
        fetchAlbums(sortType)
      } else if (type === 'song') {
        const sortType = getActiveSortType()
        console.log('获取歌曲数据，排序类型:', sortType)
        fetchSongs(sortType)
      }
    })
  })

  // 添加排序方式点击事件监听器
  const sortLinks = document.querySelectorAll('.secondary-nav a[data-sort]')
  sortLinks.forEach((link) => {
    link.addEventListener('click', function (e) {
      e.preventDefault()

      console.log('点击了排序链接:', this.getAttribute('data-sort'))

      // 移除所有排序链接的活动状态
      sortLinks.forEach((l) => {
        l.classList.remove('text-[#FFD700]', 'border-b-2', 'border-[#FFD700]')
        l.classList.add('text-[#CCCCCC]')
      })

      // 为当前点击的链接添加活动状态
      this.classList.remove('text-[#CCCCCC]')
      this.classList.add('text-[#FFD700]', 'border-b-2', 'border-[#FFD700]')

      // 根据内容类型重新获取数据
      const sortType = this.getAttribute('data-sort')
      console.log('排序类型变更:', sortType, '当前内容类型:', contentType.value)
      // 重置页码
      currentPage.value = 1
      totalPages.value = 1
      if (contentType.value === 'album') {
        fetchAlbums(sortType)
      } else if (contentType.value === 'song') {
        fetchSongs(sortType)
      }
    })
  })

  // 初始加载专辑数据
  console.log('初始加载专辑数据')
  fetchAlbums()
})
</script>

<style scoped>
/* 专辑和歌曲网格样式 */
.content-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 24px;
}

.content-card:hover .play-button {
  opacity: 1;
  transform: translate(-50%, -50%) scale(1);
}

.content-card:hover img {
  transform: scale(1.05);
  filter: brightness(0.7);
}

.content-cover {
  position: relative;
  overflow: hidden;
  border-radius: 8px;
}

.play-button {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%) scale(0.8);
  width: 50px;
  height: 50px;
  border-radius: 50%;
  background: rgba(255, 215, 0, 0.9);
  display: flex;
  align-items: center;
  justify-content: center;
  color: #0a0a0a;
  opacity: 0;
  transition: all 0.3s ease;
  z-index: 2;
}

/* 可点击元素样式 */
.clickable {
  cursor: pointer;
  transition: color 0.3s ease;
}

.clickable:hover {
  color: #ffd700 !important;
  text-decoration: underline;
}

/* 评分样式 */
.rating-badge {
  position: absolute;
  top: 8px;
  right: 8px;
  background: rgba(0, 0, 0, 0.7);
  color: #ffd700;
  border-radius: 4px;
  padding: 2px 6px;
  font-size: 12px;
  font-weight: bold;
  z-index: 1;
}

/* 分页样式 */
.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 40px;
  gap: 8px;
}

.pagination-btn {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #1f1f1f;
  color: #cccccc;
  border: none;
  cursor: pointer;
}

.pagination-btn:hover:not(:disabled) {
  background: #333;
  color: #ffd700;
}

.pagination-btn.active {
  background: #ffd700;
  color: #0a0a0a;
  font-weight: bold;
}

.pagination-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.pagination-ellipsis {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 36px;
  height: 36px;
  color: #cccccc;
}

.artist-name {
  font-style: italic;
  position: relative;
  display: inline-block;
}

@media (max-width: 1024px) {
  .container {
    padding: 0 20px;
  }

  .content-grid {
    grid-template-columns: repeat(auto-fill, minmax(160px, 1fr));
    gap: 16px;
  }
}
</style>
