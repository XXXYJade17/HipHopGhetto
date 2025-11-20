<template>
  <div class="container py-8">
    <!-- 返回按钮 -->
    <button @click="goBack" class="outline-btn px-4 py-2 mb-6 flex items-center">
      <iconify-icon icon="mdi:arrow-left" width="20" class="mr-2"></iconify-icon>
      返回专辑列表
    </button>

    <!-- 加载状态 -->
    <div v-if="loading" class="flex justify-center items-center py-16">
      <div class="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-[#FFD700]"></div>
    </div>

    <!-- 错误状态 -->
    <div v-else-if="error" class="text-center py-16">
      <p class="text-[#FF6B6B]">{{ error }}</p>
      <button @click="fetchAlbumDetail" class="mt-4 px-6 py-2 main-btn">重试</button>
    </div>

    <!-- 专辑详情内容 -->
    <div v-else id="albumDetail" class="card-bg rounded-xl overflow-hidden shadow-2xl">
      <div class="md:flex">
        <!-- 专辑封面 -->
        <div class="md:w-1/3 p-6 flex flex-col items-center">
          <div class="w-full max-w-md relative">
            <img
              id="detailAlbumCover"
              :src="albumCoverUrl"
              :alt="albumName"
              class="w-full rounded-xl shadow-lg object-cover aspect-square"
            />
            <button class="main-btn w-full mt-6 py-3 flex items-center justify-center">
              <iconify-icon icon="mdi:play" width="24" class="mr-2"></iconify-icon>
              播放专辑
            </button>
          </div>
        </div>

        <!-- 专辑信息 -->
        <div class="md:w-2/3 p-6">
          <div class="mb-6">
            <h3 id="detailAlbumName" class="text-3xl font-bold text-white mb-2">
              {{ albumName }}
            </h3>
            <p class="text-[#FFD700] text-lg mb-4">
              <span class="artist-name" id="detailAlbumSinger">{{ artists }}</span>
            </p>
            <div class="flex flex-wrap items-center text-gray-400 mb-6">
              <span>{{ releaseTime }}</span>
              <span class="mx-2">•</span>
              <span>{{ albumDetail.songs?.length }} 首歌曲</span>
            </div>

            <!-- 操作按钮 -->
            <div class="flex space-x-4 mb-6">
              <button
                @click="toggleCollect"
                class="flex items-center px-3 py-1.5 bg-[#1F1F1F] text-white rounded-lg hover:bg-[#333] transition-all duration-300 group relative"
              >
                <iconify-icon
                  :icon="isCollected ? 'mdi:heart' : 'mdi:heart-outline'"
                  width="20"
                  class="mr-1 transition-all duration-300 ease-in-out group-hover:scale-110"
                  :class="{
                    'text-[#FF6B6B] scale-110 animate-pulse-custom': isCollected,
                  }"
                ></iconify-icon>
                <span>({{ albumDetail.collectionCount || 0 }})</span>
              </button>

              <button
                @click="goToComments"
                class="flex items-center px-3 py-1.5 bg-[#1F1F1F] text-white rounded-lg hover:bg-[#333] transition-colors"
              >
                <iconify-icon icon="mdi:comment-outline" width="20" class="mr-1"></iconify-icon>
                <span>({{ albumDetail.commentCount || 0 }})</span>
              </button>
            </div>
          </div>

          <p id="detailIntroduction" class="text-gray-300 mb-8 leading-relaxed">
            简介：{{ description || '暂无专辑介绍' }}
          </p>

          <!-- 专辑评分区域 -->
          <div class="rating-section bg-[#1a1a1a] rounded-xl p-6 mb-8">
            <h4 class="text-xl font-bold text-white mb-4">专辑评分</h4>
            <div class="rating-overview flex flex-col sm:flex-row items-center">
              <div class="rating-score text-5xl font-bold text-[#FFD700] mb-4 sm:mb-0 sm:mr-6">
                {{ formatScore(avgScore) }}
              </div>
              <div class="rating-stats flex-1">
                <div class="flex items-center mb-2">
                  <div class="flex mr-3">
                    <iconify-icon
                      v-for="i in 5"
                      :key="i"
                      :icon="getStarIcon(i, avgScore)"
                      width="24"
                      class="text-[#FFD700]"
                    >
                    </iconify-icon>
                  </div>
                  <span class="text-gray-300">{{ scoreCount || 0 }} 人评分</span>
                </div>
                <div class="w-full bg-[#333] rounded-full h-2 mb-4">
                  <div
                    class="bg-[#FFD700] h-2 rounded-full"
                    :style="{ width: (avgScore ? (avgScore / 10) * 100 : 0) + '%' }"
                  ></div>
                </div>

                <!-- 显示用户当前评分 -->
                <div
                  v-if="currentUserRating !== null && currentUserRating !== -1"
                  class="text-[#FFD700] font-medium"
                >
                  我的评分: {{ formatScore(currentUserRating) }} 分
                </div>
              </div>
            </div>

            <!-- 评分按钮 -->
            <div class="flex items-center justify-center mt-4 mb-2">
              <button class="text-gray-300 mr-2 main-btn px-4 py-2" @click="openRatingModal">
                {{
                  currentUserRating !== null && currentUserRating !== -1
                    ? '修改评分'
                    : '给这张专辑评分'
                }}
              </button>
            </div>
          </div>

          <!-- 歌曲列表 -->
          <div>
            <h4 class="text-xl font-bold text-white mb-4">歌曲列表</h4>
            <div id="songList" class="space-y-2">
              <div
                v-for="(song, index) in albumDetail.songs"
                :key="song.songId"
                class="flex items-center p-3 rounded-lg hover:bg-[#1F1F1F] group cursor-pointer transition-colors"
                @click="playSong(song)"
              >
                <div class="w-8 text-center text-[#808080] group-hover:text-white">
                  <span class="group-hover:hidden">{{ index + 1 }}</span>
                  <iconify-icon
                    icon="mdi:play-circle-outline"
                    width="20"
                    class="hidden group-hover:block text-[#FFD700] mx-auto"
                  >
                  </iconify-icon>
                </div>
                <div class="flex-1 ml-4">
                  <div class="text-white font-medium">{{ song.songName }}</div>
                </div>
                <div class="text-[#808080]">
                  {{ formatDuration(song.duration) }}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 评分弹窗 -->
    <div
      v-if="showRatingModal"
      class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
      @keydown.esc="closeRatingModal"
      @click="onModalBackdropClick"
      tabindex="0"
    >
      <div class="bg-[#1a1a1a] rounded-xl p-6 w-full max-w-md" @click.stop>
        <div class="flex justify-between items-center mb-4">
          <h3 class="text-xl font-bold text-white">给专辑评分</h3>
          <button @click="closeRatingModal" class="text-gray-400 hover:text-white">
            <iconify-icon icon="mdi:close" width="24"></iconify-icon>
          </button>
        </div>

        <div class="mb-4">
          <p class="text-gray-300 mb-2">{{ albumName }}</p>
          <div class="star-rating flex mb-3">
            <span
              v-for="i in 10"
              :key="i"
              class="star cursor-pointer text-2xl mr-1 transition-colors"
              :data-value="i"
              @click="setRating(i)"
              :class="{ 'text-[#FFD700]': i <= userRating, 'text-gray-600': i > userRating }"
            >
              <iconify-icon icon="mdi:star" width="24"></iconify-icon>
            </span>
          </div>
        </div>

        <div class="flex justify-between items-center">
          <button @click="closeRatingModal" class="outline-btn px-4 py-2" :disabled="ratingLoading">
            取消
          </button>
          <button
            @click="submitRating"
            class="main-btn px-4 py-2 flex items-center"
            :disabled="ratingLoading"
            :class="{ 'opacity-50 cursor-not-allowed': ratingLoading }"
          >
            <span v-if="ratingLoading">提交中...</span>
            <span v-else>提交评分</span>
          </button>
        </div>

        <div
          v-if="ratingMessage"
          class="mt-3 text-center text-sm"
          :class="ratingMessage.includes('成功') ? 'text-green-500' : 'text-red-500'"
        >
          {{ ratingMessage }}
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { albumApi } from '@/api'

const route = useRoute()
const router = useRouter()

// 专辑详情数据
const albumDetail = ref({})

// 收藏状态
const collectStatus = ref(false)

// 加载状态
const loading = ref(true)

// 错误信息
const error = ref(null)

// 用户评分
const userRating = ref(0)

// 用户当前评分（从接口获取）
const currentUserRating = ref(null)

// 评分弹窗显示状态
const showRatingModal = ref(false)

// 评分加载状态
const ratingLoading = ref(false)

// 评分提交结果消息
const ratingMessage = ref('')

// 从专辑详情中提取信息
const albumName = computed(() => {
  return albumDetail.value.albumName || ''
})

const artists = computed(() => {
  // 添加调试日志
  console.log('Album detail data:', albumDetail.value)
  return albumDetail.value.artists || '未知歌手'
})

const albumCoverUrl = computed(() => {
  return albumDetail.value.coverUrl || ''
})

const description = computed(() => {
  return albumDetail.value.description || ''
})

const releaseTime = computed(() => {
  return albumDetail.value.releaseTime || ''
})

const avgScore = computed(() => {
  // 直接从专辑详情中获取评分
  return albumDetail.value.avgScore
})

const scoreCount = computed(() => {
  // 直接从专辑详情中获取评分人数
  return albumDetail.value.scoreCount
})

// 判断是否已收藏
const isCollected = computed(() => {
  // 使用专门的收藏状态接口获取的值
  return collectStatus.value
})

// 格式化评分显示
const formatScore = (score) => {
  if (score === null || score === undefined) {
    return '暂无评分'
  }
  // 后端已经处理过评分数据，直接格式化为一位小数
  return score.toFixed(1)
}

// 获取星星图标
const getStarIcon = (index, avgScore) => {
  if (!avgScore) return 'mdi:star-outline'

  const filledStars = Math.floor(avgScore / 2)
  const hasHalfStar = (avgScore / 2) % 1 >= 0.5

  if (index <= filledStars) {
    return 'mdi:star'
  } else if (index === filledStars + 1 && hasHalfStar) {
    return 'mdi:star-half'
  } else {
    return 'mdi:star-outline'
  }
}

// 格式化时长显示
const formatDuration = (seconds) => {
  const mins = Math.floor(seconds / 60)
  const secs = seconds % 60
  return `${mins}:${secs.toString().padStart(2, '0')}`
}

// 设置用户评分
const setRating = (rating) => {
  userRating.value = rating
}

// 检查用户是否已登录
const checkAuth = () => {
  const token = localStorage.getItem('authentication')
  return !!token
}

// 切换收藏状态
const toggleCollect = async () => {
  if (!checkAuth()) {
    // 如果未登录，跳转到登录页面
    router.push('/auth')
    return
  }

  try {
    // 根据当前收藏状态决定调用哪个API
    let response
    if (isCollected.value) {
      // 已收藏，需要取消收藏
      response = await albumApi.uncollectAlbum(route.params.id)
    } else {
      // 未收藏，需要收藏
      response = await albumApi.collectAlbum(route.params.id)
    }

    if (response.code === 200) {
      // 更新收藏状态
      // 切换收藏状态：如果已收藏则取消收藏，如果未收藏则收藏
      collectStatus.value = !isCollected.value

      // 更新收藏数
      if (isCollected.value) {
        albumDetail.value.collectionCount = (albumDetail.value.collectionCount || 0) + 1
        // 显示收藏成功提示
        showCollectMessage('收藏成功')
      } else {
        albumDetail.value.collectionCount = Math.max(0, (albumDetail.value.collectionCount || 0) - 1)
        // 显示取消收藏提示
        showCollectMessage('已取消收藏')
      }
    } else {
      // 处理收藏失败
      showCollectMessage(response.message || '操作失败')
    }
  } catch (err) {
    console.error('收藏操作失败:', err)

    // 统一处理401错误（未授权）
    // 检查各种可能的401错误形式
    if (
      err.code === 401 ||
      (err.response && err.response.status === 401) ||
      (err.message && err.message.includes('401'))
    ) {
      showCollectMessage('登录已过期，请重新登录')
      localStorage.removeItem('authentication')
      setTimeout(() => {
        router.push('/auth')
      }, 1500)
      return
    }

    // 处理其他错误
    showCollectMessage('操作失败，请稍后重试')
  }
}

// 显示收藏提示消息
const showCollectMessage = (message) => {
  // 创建临时提示元素
  const messageEl = document.createElement('div')
  messageEl.className =
    'fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 bg-[#1a1a1a] text-white px-6 py-3 rounded-xl shadow-2xl z-50 transition-all duration-300 font-medium'
  messageEl.textContent = message
  messageEl.style.opacity = '0'
  messageEl.style.pointerEvents = 'none'
  messageEl.style.boxShadow = '0 10px 25px rgba(0, 0, 0, 0.5)'
  document.body.appendChild(messageEl)

  // 渐入动画
  setTimeout(() => {
    messageEl.style.opacity = '1'
    messageEl.style.transform = 'translate(-50%, -50%) scale(1)'
  }, 10)

  // 渐出动画并移除元素
  setTimeout(() => {
    messageEl.style.opacity = '0'
    messageEl.style.transform = 'translate(-50%, -50%) scale(0.8)'
    setTimeout(() => {
      if (messageEl.parentNode) {
        messageEl.parentNode.removeChild(messageEl)
      }
    }, 300)
  }, 1500)
}

// 跳转到评论页面
const goToComments = () => {
  router.push({
    path: `/album/${route.params.id}/comments`,
    query: {
      commentSectionId: albumDetail.value.commentSectionId,
    },
  })
}

// 打开评分弹窗
const openRatingModal = async () => {
  // 检查用户是否已登录
  if (!checkAuth()) {
    ratingMessage.value = '请先登录后再评分'
    setTimeout(() => {
      router.push('/auth')
    }, 1500)
    return
  }

  // 使用从API获取的用户评分
  if (currentUserRating.value !== null && currentUserRating.value !== -1) {
    userRating.value = currentUserRating.value
  } else {
    userRating.value = 0
  }

  showRatingModal.value = true
}

// 关闭评分弹窗
const closeRatingModal = () => {
  showRatingModal.value = false
  ratingMessage.value = ''
}

// 点击遮罩层关闭弹窗
const onModalBackdropClick = (event) => {
  if (event.target === event.currentTarget) {
    closeRatingModal()
  }
}

// 监听弹窗状态变化
watch(showRatingModal, (newVal) => {
  if (newVal) {
    // 弹窗打开时，聚焦到弹窗上以支持键盘事件
    setTimeout(() => {
      const modal = document.querySelector('.fixed.inset-0')
      if (modal) {
        modal.focus()
      }
    }, 0)
  }
})

// 提交或更新评分
const submitRating = async () => {
  // 检查用户是否已登录
  if (!checkAuth()) {
    ratingMessage.value = '请先登录后再评分'
    setTimeout(() => {
      router.push('/auth')
    }, 1500)
    return
  }

  if (userRating.value < 1 || userRating.value > 10) {
    ratingMessage.value = '请选择1-10分之间的评分'
    return
  }

  try {
    ratingLoading.value = true
    ratingMessage.value = ''

    await albumApi.submitAlbumScore({
      targetId: route.params.id,
      score: userRating.value,
    })
    ratingMessage.value = currentUserRating.value ? '评分更新成功' : '评分提交成功'

    // 更新用户评分状态
    currentUserRating.value = userRating.value

    // 重新获取专辑详情以更新评分
    await fetchAlbumDetail()

    // 延迟关闭弹窗，让用户看到成功消息
    setTimeout(() => {
      if (showRatingModal.value) {
        closeRatingModal()
      }
    }, 2000)
  } catch (err) {
    console.error('评分提交失败:', err)

    // 统一处理401错误（未授权）
    // 检查各种可能的401错误形式
    if (
      err.code === 401 ||
      (err.response && err.response.status === 401) ||
      (err.message && err.message.includes('401'))
    ) {
      ratingMessage.value = '登录已过期，请重新登录'
      localStorage.removeItem('authentication')
      setTimeout(() => {
        router.push('/auth')
      }, 1500)
      return
    }

    // 处理其他错误
    ratingMessage.value = err.message || '评分提交失败，请稍后重试'
  } finally {
    ratingLoading.value = false
  }
}

// 播放歌曲
const playSong = (song) => {
  console.log('播放歌曲:', song)
  // 这里可以集成到音乐播放器中
  alert(`正在播放: ${song.songName}`)
}

// 获取专辑详情
const fetchAlbumDetail = async () => {
  try {
    loading.value = true
    error.value = null

    console.log('正在获取专辑详情，ID:', route.params.id)
    const response = await albumApi.getAlbumDetail(route.params.id)
    console.log('API响应:', response)

    // 由于添加了全局拦截器，成功的响应会直接返回数据
    albumDetail.value = response.data
    // 确保有默认的收藏数和评论数
    if (albumDetail.value.collectionCount === undefined) {
      albumDetail.value.collectionCount = 0
    }
    if (albumDetail.value.commentCount === undefined) {
      albumDetail.value.commentCount = 0
    }
    console.log('专辑详情数据:', albumDetail.value)
  } catch (err) {
    console.error('获取专辑详情失败:', err)

    // 统一处理401错误（未授权）
    // 检查各种可能的401错误形式
    if (
      err.code === 401 ||
      (err.response && err.response.status === 401) ||
      (err.message && err.message.includes('401'))
    ) {
      error.value = '登录已过期，请重新登录'
      localStorage.removeItem('authentication')
      setTimeout(() => {
        router.push('/auth')
      }, 1500)
      return
    }

    // 处理其他错误
    error.value = err.message || '获取专辑详情失败，请稍后重试'
  } finally {
    loading.value = false
  }
}

// 获取用户对专辑的评分
const fetchUserAlbumScore = async () => {
  try {
    console.log('正在获取用户对专辑的评分，ID:', route.params.id)
    const response = await albumApi.getUserAlbumScore(route.params.id)
    console.log('评分API响应:', response)

    if (response.code === 200) {
      // 如果用户有评分，更新currentUserRating
      if (response.data !== null && response.data !== undefined && response.data !== -1) {
        currentUserRating.value = response.data
        console.log('用户评分:', currentUserRating.value)
      } else {
        // 如果用户没有评分或评分为-1，设置为null
        currentUserRating.value = null
        console.log('用户尚未评分')
      }
    } else {
      // 如果获取评分失败，设置为null
      currentUserRating.value = null
      console.log('获取用户评分失败:', response.message)
    }
  } catch (err) {
    console.error('获取用户评分失败:', err)

    // 统一处理401错误（未授权）
    // 检查各种可能的401错误形式
    if (
      err.code === 401 ||
      (err.response && err.response.status === 401) ||
      (err.message && err.message.includes('401'))
    ) {
      error.value = '登录已过期，请重新登录'
      localStorage.removeItem('authentication')
      setTimeout(() => {
        router.push('/auth')
      }, 1500)
      return
    }

    // 出错时也设置为null
    currentUserRating.value = null
  }
}

// 获取专辑收藏状态
const fetchAlbumCollectStatus = async () => {
  try {
    console.log('正在获取专辑收藏状态，ID:', route.params.id)
    const response = await albumApi.getAlbumCollectStatus(route.params.id)
    console.log('收藏状态API响应:', response)

    if (response.code === 200) {
      // 更新收藏状态
      collectStatus.value = response.data === true
      console.log('专辑收藏状态:', collectStatus.value)
    } else {
      // 获取失败，默认为未收藏
      collectStatus.value = false
      console.log('获取收藏状态失败:', response.message)
    }
  } catch (err) {
    console.error('获取专辑收藏状态失败:', err)

    // 统一处理401错误（未授权）
    // 检查各种可能的401错误形式
    if (
      err.code === 401 ||
      (err.response && err.response.status === 401) ||
      (err.message && err.message.includes('401'))
    ) {
      error.value = '登录已过期，请重新登录'
      localStorage.removeItem('authentication')
      setTimeout(() => {
        router.push('/auth')
      }, 1500)
      return
    }

    // 获取失败，默认为未收藏
    collectStatus.value = false
  }
}

// 返回上一页
const goBack = () => {
  router.back()
}

onMounted(() => {
  Promise.all([fetchAlbumDetail(), fetchUserAlbumScore(), fetchAlbumCollectStatus()])
})
</script>

<style scoped>
.card-bg {
  background: #121212;
}

.outline-btn {
  border: 1px solid #ffd700;
  color: #ffd700;
  border-radius: 0.5rem;
  transition: all 0.3s ease;
  font-weight: 500;
}

.outline-btn:hover {
  background: #ffd700;
  color: #0a0a0a;
}

.main-btn {
  background: #ffd700;
  color: #0a0a0a;
  border-radius: 0.5rem;
  font-weight: bold;
  transition: all 0.3s ease;
}

.main-btn:hover {
  background: #ffc400;
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(255, 215, 0, 0.3);
}

.rating-score {
  line-height: 1;
}

.rating-overview {
  gap: 1rem;
}

.rating-stats {
  flex: 1;
}

.star-rating .star {
  cursor: pointer;
  transition: color 0.2s ease;
}

.star-rating .star:hover {
  color: #ffd700;
}

.rating-submit {
  background: #ffd700;
  color: #0a0a0a;
  border: none;
  border-radius: 0.25rem;
  font-weight: bold;
  cursor: pointer;
  transition: background 0.3s ease;
}

.rating-submit:hover {
  background: #ffc400;
}

/* 弹窗背景可点击关闭 */
.fixed.inset-0.bg-black.bg-opacity-50 {
  cursor: pointer;
}

/* 收藏成功动画 */
@keyframes pulse {
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.2);
  }
  100% {
    transform: scale(1);
  }
}

.animate-pulse-custom {
  animation: pulse 0.3s ease-in-out;
}
</style>
