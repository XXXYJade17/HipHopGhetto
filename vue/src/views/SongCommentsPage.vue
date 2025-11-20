<template>
  <div class="container py-8">
    <!-- 返回按钮 -->
    <button @click="goBack" class="outline-btn px-4 py-2 mb-6 flex items-center">
      <iconify-icon icon="mdi:arrow-left" width="20" class="mr-2"></iconify-icon>
      返回歌曲详情
    </button>

    <!-- 页面标题 -->
    <div class="flex items-center justify-between mb-8">
      <h1 class="text-3xl font-bold text-white">歌曲评论</h1>
      <button
        v-if="!showCommentForm"
        @click="showCommentForm = true"
        class="main-btn px-4 py-2 flex items-center"
      >
        <iconify-icon icon="mdi:plus" width="20" class="mr-1"></iconify-icon>
        写评论
      </button>
    </div>

    <!-- 写评论表单 -->
    <div v-if="showCommentForm" class="card-bg rounded-xl p-6 mb-8">
      <h2 class="text-xl font-bold text-white mb-4">发表评论</h2>
      <div class="mb-4">
        <textarea
          v-model="newComment"
          placeholder="分享你对这首歌的看法..."
          class="w-full bg-[#1F1F1F] text-white rounded-lg p-4 focus:outline-none focus:ring-2 focus:ring-[#FFD700]"
          rows="4"
        ></textarea>
      </div>
      <div class="flex justify-end space-x-3">
        <button @click="showCommentForm = false" class="outline-btn px-4 py-2">取消</button>
        <button
          @click="submitComment"
          :disabled="commentLoading"
          class="main-btn px-4 py-2 flex items-center"
          :class="{ 'opacity-50 cursor-not-allowed': commentLoading }"
        >
          <span v-if="commentLoading">提交中...</span>
          <span v-else>发表评论</span>
        </button>
      </div>
    </div>

    <!-- 评论列表 -->
    <div class="card-bg rounded-xl overflow-hidden">
      <div v-if="loading" class="flex justify-center items-center py-16">
        <div
          class="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-[#FFD700]"
        ></div>
      </div>

      <div v-else-if="error" class="text-center py-16">
        <p class="text-[#FF6B6B]">{{ error }}</p>
        <button @click="fetchComments" class="mt-4 px-6 py-2 main-btn">重试</button>
      </div>

      <div v-else>
        <div v-if="comments.length === 0" class="text-center py-16 text-gray-400">
          暂无评论，快来发表第一条评论吧！
        </div>

        <div v-else>
          <div
            v-for="comment in comments"
            :key="comment.id"
            class="border-b border-[#333] last:border-b-0 p-6"
          >
            <div class="flex items-start">
              <div class="w-10 h-10 rounded-full bg-[#333] flex items-center justify-center mr-4">
                <iconify-icon icon="mdi:account" width="24" class="text-[#808080]"></iconify-icon>
              </div>
              <div class="flex-1">
                <div class="flex items-center mb-2">
                  <span class="text-white font-medium mr-3">{{ comment.username }}</span>
                  <span class="text-gray-500 text-sm">{{ formatDate(comment.createTime) }}</span>
                </div>
                <p class="text-gray-300 mb-3">{{ comment.content }}</p>
                <div class="flex items-center">
                  <button
                    @click="likeComment(comment)"
                    class="flex items-center text-gray-500 hover:text-[#FFD700] transition-colors"
                    :class="{ 'text-[#FFD700]': comment.liked }"
                  >
                    <iconify-icon
                      :icon="comment.liked ? 'mdi:thumb-up' : 'mdi:thumb-up-outline'"
                      width="18"
                      class="mr-1"
                    ></iconify-icon>
                    <span>{{ comment.likeCount }}</span>
                  </button>
                  <button
                    class="flex items-center text-gray-500 hover:text-[#FFD700] transition-colors ml-4"
                  >
                    <iconify-icon icon="mdi:comment-outline" width="18" class="mr-1"></iconify-icon>
                    <span>{{ comment.replyCount }}</span>
                  </button>
                </div>
              </div>
            </div>
          </div>

          <!-- 分页 -->
          <div v-if="totalPages > 1" class="flex justify-center items-center py-6">
            <button
              @click="goToPage(currentPage - 1)"
              :disabled="currentPage === 1"
              class="pagination-btn mr-2"
              :class="{ 'opacity-50 cursor-not-allowed': currentPage === 1 }"
            >
              <iconify-icon icon="mdi:chevron-left" width="16"></iconify-icon>
            </button>

            <span class="text-white mx-4"> {{ currentPage }} / {{ totalPages }} </span>

            <button
              @click="goToPage(currentPage + 1)"
              :disabled="currentPage === totalPages"
              class="pagination-btn ml-2"
              :class="{ 'opacity-50 cursor-not-allowed': currentPage === totalPages }"
            >
              <iconify-icon icon="mdi:chevron-right" width="16"></iconify-icon>
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- 评论统计 -->
    <div
      v-if="!loading && !error && comments.length > 0"
      class="mt-4 text-center text-gray-500 text-sm"
    >
      共 {{ totalComments }} 条评论
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { songApi } from '@/api'

const route = useRoute()
const router = useRouter()

// 评论数据
const comments = ref([])
const loading = ref(true)
const error = ref(null)

// 分页相关
const currentPage = ref(1)
const totalPages = ref(1)
const totalComments = ref(0)

// 评论表单相关
const showCommentForm = ref(false)
const newComment = ref('')
const commentLoading = ref(false)

// 评论区ID
const commentSectionId = ref(route.query.commentSectionId || null)

// 格式化日期
const formatDate = (dateString) => {
  // 检查日期字符串是否有效
  if (!dateString) {
    return '未知时间'
  }

  const date = new Date(dateString)

  // 检查日期对象是否有效
  if (isNaN(date.getTime())) {
    return '未知时间'
  }

  const now = new Date()
  const diffInMinutes = Math.floor((now - date) / (1000 * 60))

  if (diffInMinutes < 1) {
    return '刚刚'
  } else if (diffInMinutes < 60) {
    return `${diffInMinutes}分钟前`
  } else if (diffInMinutes < 1440) {
    // 24小时内
    const diffInHours = Math.floor(diffInMinutes / 60)
    return `${diffInHours}小时前`
  } else {
    return date.toLocaleDateString('zh-CN')
  }
}

// 获取当前用户名
const getCurrentUsername = () => {
  // 尝试从localStorage获取用户名
  const storedUsername = localStorage.getItem('username')
  if (storedUsername) {
    return storedUsername
  }

  // 如果没有存储用户名，则返回默认值
  return '当前用户'
}

// 获取评论列表
const fetchComments = async () => {
  try {
    if (!commentSectionId.value) {
      throw new Error('缺少评论区ID')
    }

    loading.value = true
    error.value = null

    const response = await songApi.getSongComments({
      sortType: 'DEFAULT',
      page: currentPage.value,
      size: 10,
      commentSectionId: commentSectionId.value,
    })

    if (response.code === 200) {
      const result = response.data
      // 确保每个评论都有createTime字段
      const commentsWithCreateTime = (result.data || []).map((comment) => ({
        ...comment,
        createTime: comment.createTime || new Date().toISOString(),
      }))

      comments.value = commentsWithCreateTime
      totalComments.value = result.total || 0
      totalPages.value = Math.ceil(totalComments.value / 10)
    } else {
      throw new Error(response.message || '获取评论失败')
    }
  } catch (err) {
    console.error('获取评论失败:', err)
    error.value = '获取评论失败，请稍后重试'
    comments.value = []
  } finally {
    loading.value = false
  }
}

// 提交评论
const submitComment = async () => {
  if (!newComment.value.trim()) {
    return
  }

  if (!commentSectionId.value) {
    console.error('缺少评论区ID')
    return
  }

  try {
    commentLoading.value = true

    const requestData = {
      commentSectionId: commentSectionId.value,
      content: newComment.value,
    }

    const response = await songApi.createSongComment(requestData)

    if (response.code === 200) {
      // 构造一个完整的评论对象
      const newCommentData = {
        id: response.data.id || Date.now(), // 使用时间戳作为临时ID
        userId: response.data.userId || 0,
        username: response.data.username || getCurrentUsername() || '匿名用户',
        commentSectionId: response.data.commentSectionId || commentSectionId.value,
        replyId: response.data.replyId || 0,
        replyCount: response.data.replyCount || 0,
        content: response.data.content || newComment.value,
        createTime: response.data.createTime || new Date().toISOString(),
        likeCount: response.data.likeCount || 0,
        liked: response.data.liked || false,
      }

      // 直接修改数组并触发Vue响应式更新
      comments.value.unshift(newCommentData)

      // 清空表单和关闭模态框
      newComment.value = ''
      showCommentForm.value = false
      totalComments.value++

      // 更新总页数
      totalPages.value = Math.ceil(totalComments.value / 10)
    } else {
      throw new Error(response.message || '发表评论失败')
    }
  } catch (err) {
    console.error('发表评论失败:', err)
    // 可以在这里添加错误提示
  } finally {
    commentLoading.value = false
  }
}

// 点赞评论
const likeComment = (comment) => {
  // 模拟点赞功能
  if (comment.liked) {
    comment.likeCount--
    comment.liked = false
  } else {
    comment.likeCount++
    comment.liked = true
  }
}

// 分页跳转
const goToPage = (page) => {
  if (page < 1 || page > totalPages.value) return
  currentPage.value = page
  fetchComments()
}

// 返回上一页
const goBack = () => {
  router.back()
}

onMounted(() => {
  fetchComments()
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
</style>
