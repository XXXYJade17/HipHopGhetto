<template>
  <div class="forum-page">
    <div class="container mx-auto px-4 py-8">
      <!-- 轮播图 -->
      <div class="relative overflow-hidden rounded-xl mb-8 h-96">
        <!-- 轮播图容器 -->
        <div
          class="flex transition-transform duration-500 ease-in-out"
          :style="{ transform: `translateX(-${currentIndex * 100}%)` }"
        >
          <!-- 轮播项 -->
          <div v-for="post in hotPosts" :key="post.id" class="w-full flex-shrink-0 relative">
            <img :src="post.image" :alt="post.title" class="w-full h-full object-cover" />
            <div
              class="absolute inset-0 bg-gradient-to-t from-black to-transparent opacity-80"
            ></div>
            <div class="absolute bottom-0 left-0 p-8 w-full" style="bottom: 3rem">
              <div
                class="mb-2 px-3 py-1 bg-red-600 rounded-full text-sm font-bold text-white inline-block"
              >
                热门
              </div>
              <h2 class="text-3xl font-bold text-white mb-2">{{ post.title }}</h2>
              <p class="text-gray-200 mb-4">{{ post.excerpt }}</p>
              <button
                @click="viewPost(post.id)"
                class="main-btn px-6 py-2 rounded-lg flex items-center z-10 relative"
              >
                <iconify-icon icon="mdi:play" width="20" class="mr-2"></iconify-icon>
                查看详情
              </button>
            </div>
          </div>
        </div>

        <!-- 导航按钮 -->
        <button
          @click="prevSlide"
          class="absolute left-4 top-1/2 transform -translate-y-1/2 bg-black bg-opacity-50 text-white p-2 rounded-full hover:bg-opacity-75 transition-all z-10"
        >
          <iconify-icon icon="mdi:chevron-left" width="24"></iconify-icon>
        </button>
        <button
          @click="nextSlide"
          class="absolute right-4 top-1/2 transform -translate-y-1/2 bg-black bg-opacity-50 text-white p-2 rounded-full hover:bg-opacity-75 transition-all z-10"
        >
          <iconify-icon icon="mdi:chevron-right" width="24"></iconify-icon>
        </button>

        <!-- 指示器 -->
        <div class="absolute bottom-4 left-1/2 transform -translate-x-1/2 flex space-x-2 z-10">
          <div
            v-for="(_, idx) in hotPosts"
            :key="idx"
            @click="goToSlide(idx)"
            class="w-3 h-3 rounded-full cursor-pointer transition-all"
            :class="idx === currentIndex ? 'bg-[#FFD700] w-6' : 'bg-white bg-opacity-50'"
          ></div>
        </div>
      </div>

      <!-- 热门话题列表 -->
      <div class="mt-16">
        <h2 class="text-2xl font-bold text-gray-800 mb-6">所有热门话题</h2>
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <div
            v-for="post in hotPosts"
            :key="post.id"
            @click="viewPost(post.id)"
            class="bg-white rounded-lg shadow-md overflow-hidden cursor-pointer hover:shadow-lg transition-shadow duration-300"
          >
            <img :src="post.image" :alt="post.title" class="w-full h-48 object-cover" />
            <div class="p-6">
              <div class="flex justify-between items-start mb-3">
                <h3 class="text-xl font-bold text-gray-800 line-clamp-1">{{ post.title }}</h3>
                <span class="bg-red-100 text-red-800 text-xs font-bold px-2 py-1 rounded-full ml-2"
                  >HOT</span
                >
              </div>
              <p class="text-gray-600 mb-4 line-clamp-2">{{ post.excerpt }}</p>
              <div class="flex justify-between items-center text-sm text-gray-500">
                <span>{{ post.author }}</span>
                <div class="flex items-center">
                  <i class="far fa-eye mr-1"></i>
                  <span>{{ post.views }}</span>
                  <i class="far fa-comment ml-3 mr-1"></i>
                  <span>{{ post.comments }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 悬浮加号按钮 -->
    <button
      @click="openCreatePostModal"
      class="fixed bottom-20 right-8 w-14 h-14 rounded-full bg-[#FFD700] text-[#0a0a0a] flex items-center justify-center shadow-lg hover:bg-[#ffc400] transition-all z-50"
    >
      <iconify-icon icon="mdi:plus" width="28"></iconify-icon>
    </button>

    <!-- 创建话题弹窗 -->
    <div
      v-if="showCreatePostModal"
      class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
      @keydown.esc="closeCreatePostModal"
      @click="onModalBackdropClick"
    >
      <div class="bg-[#1F1F1F] rounded-xl p-6 w-full max-w-md" @click.stop>
        <div class="flex justify-between items-center mb-4">
          <h3 class="text-xl font-bold text-white">创建新话题</h3>
          <button @click="closeCreatePostModal" class="text-gray-400 hover:text-white">
            <iconify-icon icon="mdi:close" width="24"></iconify-icon>
          </button>
        </div>

        <form @submit.prevent="submitNewPost">
          <div class="space-y-4 mb-6">
            <div>
              <label class="block text-sm font-medium text-white mb-2">封面图片（可选）</label>
              <div
                class="border-2 border-dashed border-gray-600 rounded-lg p-4 text-center cursor-pointer hover:border-[#FFD700] transition-colors"
                @click="triggerFileInput"
                @paste="handlePaste"
              >
                <input
                  ref="fileInput"
                  type="file"
                  accept="image/*"
                  class="hidden"
                  @change="handleFileSelect"
                />
                <div v-if="!newPostForm.image" class="text-gray-400">
                  <iconify-icon
                    icon="mdi:cloud-upload"
                    width="48"
                    class="mx-auto mb-2"
                  ></iconify-icon>
                  <p class="mb-1">点击选择图片或粘贴图片</p>
                  <p class="text-sm">支持 JPG、PNG 格式</p>
                </div>
                <div v-else class="text-center">
                  <img :src="newPostForm.image" alt="预览图片" class="max-h-40 mx-auto rounded" />
                  <p class="text-gray-400 mt-2 text-sm">点击更换图片</p>
                </div>
              </div>
            </div>

            <div>
              <label class="block text-sm font-medium text-white mb-2">话题标题</label>
              <input
                v-model="newPostForm.title"
                type="text"
                class="w-full bg-[#2D2D2D] border border-[#333] rounded-lg py-3 px-4 focus:border-[#FFD700] focus:outline-none text-white"
                placeholder="请输入话题标题"
                required
              />
            </div>

            <div>
              <label class="block text-sm font-medium text-white mb-2">话题内容</label>
              <textarea
                v-model="newPostForm.content"
                rows="4"
                class="w-full bg-[#2D2D2D] border border-[#333] rounded-lg py-3 px-4 focus:border-[#FFD700] focus:outline-none text-white"
                placeholder="请输入话题内容"
                required
              ></textarea>
            </div>
          </div>

          <div class="flex justify-end space-x-3">
            <button type="button" @click="closeCreatePostModal" class="outline-btn px-4 py-2">
              取消
            </button>
            <button
              type="submit"
              :disabled="createPostLoading"
              class="main-btn px-4 py-2 flex items-center"
              :class="{ 'opacity-50 cursor-not-allowed': createPostLoading }"
            >
              <span v-if="createPostLoading">提交中...</span>
              <span v-else>发布话题</span>
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'

export default {
  name: 'ForumPage',
  setup() {
    const router = useRouter()
    const currentIndex = ref(0)
    const intervalId = ref(null)
    // 控制创建话题弹窗显示
    const showCreatePostModal = ref(false)
    // 创建话题表单数据
    const newPostForm = ref({
      title: '',
      content: '',
      image: '',
    })
    // 创建话题加载状态
    const createPostLoading = ref(false)
    // 文件输入引用
    const fileInput = ref(null)

    // 模拟热门帖子数据
    const hotPosts = ref([
      {
        id: 1,
        title: '如何提高Freestyle技巧？',
        excerpt: '分享一些我在练习Freestyle过程中学到的经验和技巧，帮助你快速提升即兴说唱能力。',
        image: 'https://picsum.photos/id/10/800/400',
        author: 'RapKing',
        views: '1.2k',
        comments: 42,
      },
      {
        id: 2,
        title: '2023年最受欢迎的Hip-Hop专辑排行榜',
        excerpt: '盘点今年最受欢迎的Hip-Hop专辑，从East Coast到West Coast的精彩作品都在其中。',
        image: 'https://picsum.photos/id/11/800/400',
        author: 'MusicCritic',
        views: '2.5k',
        comments: 87,
      },
      {
        id: 3,
        title: '街舞文化的历史与发展',
        excerpt: '深入探讨街舞文化的起源和发展历程，了解这一艺术形式背后的故事和社会意义。',
        image: 'https://picsum.photos/id/12/800/400',
        author: 'DanceHistorian',
        views: '3.1k',
        comments: 56,
      },
      {
        id: 4,
        title: '地下说唱与主流说唱的区别',
        excerpt: '分析地下说唱和主流说唱在内容、风格和商业性方面的差异，探讨各自的独特价值。',
        image: 'https://picsum.photos/id/13/800/400',
        author: 'UndergroundVoice',
        views: '1.8k',
        comments: 93,
      },
    ])

    // 自动轮播
    const startAutoPlay = () => {
      intervalId.value = setInterval(() => {
        nextSlide()
      }, 5000)
    }

    // 停止自动轮播
    const stopAutoPlay = () => {
      if (intervalId.value) {
        clearInterval(intervalId.value)
        intervalId.value = null
      }
    }

    // 下一张
    const nextSlide = () => {
      currentIndex.value = (currentIndex.value + 1) % hotPosts.value.length
    }

    // 上一张
    const prevSlide = () => {
      currentIndex.value = (currentIndex.value - 1 + hotPosts.value.length) % hotPosts.value.length
    }

    // 跳转到指定slide
    const goToSlide = (index) => {
      currentIndex.value = index
    }

    // 查看帖子详情
    const viewPost = (id) => {
      router.push(`/forum/post/${id}`)
    }

    // 打开创建话题弹窗
    const openCreatePostModal = () => {
      showCreatePostModal.value = true
    }

    // 关闭创建话题弹窗
    const closeCreatePostModal = () => {
      showCreatePostModal.value = false
      // 重置表单
      newPostForm.value = {
        title: '',
        content: '',
        image: '',
      }
    }

    // 点击遮罩层关闭弹窗
    const onModalBackdropClick = (event) => {
      if (event.target === event.currentTarget) {
        closeCreatePostModal()
      }
    }

    // 触发文件选择
    const triggerFileInput = () => {
      fileInput.value.click()
    }

    // 处理文件选择
    const handleFileSelect = (event) => {
      const file = event.target.files[0]
      if (file && file.type.startsWith('image/')) {
        const reader = new FileReader()
        reader.onload = (e) => {
          newPostForm.value.image = e.target.result
        }
        reader.readAsDataURL(file)
      }
      // 重置input值，以便下次选择相同文件也能触发change事件
      event.target.value = ''
    }

    // 处理粘贴事件
    const handlePaste = (event) => {
      const items = (event.clipboardData || event.originalEvent.clipboardData).items
      for (let i = 0; i < items.length; i++) {
        if (items[i].type.indexOf('image') !== -1) {
          const blob = items[i].getAsFile()
          const reader = new FileReader()
          reader.onload = (e) => {
            newPostForm.value.image = e.target.result
          }
          reader.readAsDataURL(blob)
          break
        }
      }
    }

    // 提交新话题
    const submitNewPost = async () => {
      try {
        createPostLoading.value = true

        // 在实际应用中，这里会调用API创建新话题
        // 模拟API调用
        await new Promise((resolve) => setTimeout(resolve, 1000))

        // 成功后关闭弹窗并重置表单
        closeCreatePostModal()

        // 显示成功消息（在实际应用中可能会使用全局提示组件）
        alert('话题创建成功')

        // 这里可以添加更新话题列表的逻辑
      } catch (err) {
        console.error('创建话题失败:', err)
        alert(err.message || '创建失败，请稍后重试')
      } finally {
        createPostLoading.value = false
      }
    }

    onMounted(() => {
      startAutoPlay()
    })

    onUnmounted(() => {
      stopAutoPlay()
    })

    return {
      currentIndex,
      hotPosts,
      nextSlide,
      prevSlide,
      goToSlide,
      viewPost,
      showCreatePostModal,
      newPostForm,
      createPostLoading,
      fileInput,
      openCreatePostModal,
      closeCreatePostModal,
      onModalBackdropClick,
      triggerFileInput,
      handleFileSelect,
      handlePaste,
      submitNewPost,
    }
  },
}
</script>

<style scoped>
.main-btn {
  background: linear-gradient(to right, #ffd700, #ffa500);
  border-radius: 8px;
  height: 48px;
  color: #0a0a0a;
  font-weight: 600;
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

.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.line-clamp-1 {
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>
