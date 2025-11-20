<template>
  <div class="container py-8">
    <!-- 流动展示区域 -->
    <div class="relative overflow-hidden rounded-xl mb-8 h-96">
      <!-- 轮播图容器 -->
      <div
        class="flex transition-transform duration-500 ease-in-out"
        :style="{ transform: `translateX(-${currentIndex * 100}%)` }"
      >
        <!-- 轮播项 -->
        <div
          v-for="(item, index) in carouselItems"
          :key="index"
          class="w-full flex-shrink-0 relative"
        >
          <img :src="item.image" :alt="item.title" class="w-full h-full object-cover" />
          <div class="absolute inset-0 bg-gradient-to-t from-black to-transparent opacity-80"></div>
          <div class="absolute bottom-0 left-0 p-8 w-full" style="bottom: 3rem">
            <h2 class="text-3xl font-bold text-white mb-2">{{ item.title }}</h2>
            <p class="text-gray-200 mb-4">{{ item.description }}</p>
            <button
              @click="goToDetail(item)"
              class="main-btn px-6 py-2 rounded-lg flex items-center z-10 relative"
            >
              <iconify-icon icon="mdi:play" width="20" class="mr-2"></iconify-icon>
              立即体验
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
          v-for="(item, index) in carouselItems"
          :key="index"
          @click="goToSlide(index)"
          class="w-3 h-3 rounded-full cursor-pointer transition-all"
          :class="index === currentIndex ? 'bg-[#FFD700] w-6' : 'bg-white bg-opacity-50'"
        ></div>
      </div>
    </div>

    <!-- 推荐内容 -->
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
      <div
        v-for="(item, index) in recommendedItems"
        :key="index"
        class="card-bg rounded-xl overflow-hidden hover-float cursor-pointer"
        @click="goToDetail(item)"
      >
        <img :src="item.image" :alt="item.title" class="w-full h-48 object-cover" />
        <div class="p-4">
          <h3 class="text-white font-bold mb-2 truncate">{{ item.title }}</h3>
          <p class="text-gray-400 text-sm truncate">{{ item.description }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// 轮播图数据
const carouselItems = ref([
  {
    id: 1,
    title: '最新专辑推荐',
    description: '探索最新的Hip-Hop专辑，感受音乐的魅力',
    image: 'https://placehold.co/1200x400/1a1a1a/ffd700?text=Latest+Albums',
    type: 'album',
  },
  {
    id: 2,
    title: '热门单曲榜',
    description: '本周最受欢迎的Hip-Hop单曲排行榜',
    image: 'https://placehold.co/1200x400/1a1a1a/ffd700?text=Hot+Tracks',
    type: 'song',
  },
  {
    id: 3,
    title: '艺术家特辑',
    description: '深入了解顶尖Hip-Hop艺术家的音乐世界',
    image: 'https://placehold.co/1200x400/1a1a1a/ffd700?text=Featured+Artists',
    type: 'artist',
  },
])

// 推荐内容数据
const recommendedItems = ref([
  {
    id: 1,
    title: '经典老歌回顾',
    description: '重温Hip-Hop历史上的经典作品',
    image: 'https://placehold.co/300x300/1a1a1a/ffd700?text=Classic+Hits',
    type: 'album',
  },
  {
    id: 2,
    title: '新晋艺人',
    description: '发现值得关注的新晋Hip-Hop艺人',
    image: 'https://placehold.co/300x300/1a1a1a/ffd700?text=New+Artists',
    type: 'artist',
  },
  {
    id: 3,
    title: '现场演出',
    description: '不容错过的Hip-Hop现场演出',
    image: 'https://placehold.co/300x300/1a1a1a/ffd700?text=Live+Shows',
    type: 'event',
  },
  {
    id: 4,
    title: '制作人专栏',
    description: '顶尖制作人的创作故事',
    image: 'https://placehold.co/300x300/1a1a1a/ffd700?text=Producer+Spotlight',
    type: 'producer',
  },
])

// 当前轮播图索引
const currentIndex = ref(0)

// 自动轮播定时器
let autoSlideInterval

// 下一张
const nextSlide = () => {
  currentIndex.value = (currentIndex.value + 1) % carouselItems.value.length
}

// 上一张
const prevSlide = () => {
  currentIndex.value =
    (currentIndex.value - 1 + carouselItems.value.length) % carouselItems.value.length
}

// 跳转到指定幻灯片
const goToSlide = (index) => {
  currentIndex.value = index
}

// 跳转到详情页
const goToDetail = (item) => {
  switch (item.type) {
    case 'album':
      router.push(`/album/${item.id}`)
      break
    case 'song':
      router.push(`/song/${item.id}`)
      break
    case 'artist':
      router.push(`/artist/${item.id}`)
      break
    default:
      router.push('/')
  }
}

// 开始自动轮播
const startAutoSlide = () => {
  autoSlideInterval = setInterval(() => {
    nextSlide()
  }, 5000) // 每5秒切换一次
}

// 停止自动轮播
const stopAutoSlide = () => {
  if (autoSlideInterval) {
    clearInterval(autoSlideInterval)
  }
}

// 组件挂载时开始自动轮播
onMounted(() => {
  startAutoSlide()
})

// 组件卸载时停止自动轮播
onUnmounted(() => {
  stopAutoSlide()
})
</script>

<style scoped>
.card-bg {
  background: #1f1f1f;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.5);
}

.hover-float:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.5);
}

.main-btn {
  background: linear-gradient(to right, #ffd700, #ffa500);
  border-radius: 8px;
  height: 48px;
  color: #0a0a0a;
  font-weight: 600;
}

.outline-btn {
  border: 1px solid #ffd700;
  border-radius: 8px;
  color: #ffd700;
}

.outline-btn:hover {
  background: #ffd700;
  color: #0a0a0a;
}
</style>
