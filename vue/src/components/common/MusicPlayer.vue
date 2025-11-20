<template>
  <div class="player">
    <div class="container">
      <div class="flex items-center justify-between py-3">
        <div class="flex items-center">
          <div class="w-14 h-14 rounded mr-3 overflow-hidden">
            <img
              alt="Current playing track album cover"
              class="w-full h-full object-cover"
              :src="currentSong.cover"
            />
          </div>
          <div>
            <h4 class="text-white font-medium">{{ currentSong.title }}</h4>
            <p class="text-xs text-[#808080]">
              <span class="artist-name">{{ currentSong.artist }}</span> · {{ currentSong.album }}
            </p>
          </div>
        </div>

        <div class="flex-1 max-w-lg">
          <div class="flex items-center justify-center mb-2">
            <button
              class="w-12 h-12 rounded-full flex items-center justify-center text-[#CCCCCC] hover:text-[#FFD700] mx-2"
              @click="prevSong"
            >
              <iconify-icon icon="mdi:skip-previous" width="30"></iconify-icon>
            </button>
            <button
              class="ml-4 w-12 h-12 rounded-full flex items-center justify-center hover:scale-105 mx-2"
              :class="
                playerStore.isPlaying
                  ? 'bg-white text-black'
                  : 'bg-gradient-to-r from-[#ffd700] to-[#ffa500] text-black'
              "
              @click="togglePlay"
            >
              <iconify-icon
                :icon="playerStore.isPlaying ? 'mdi:pause' : 'mdi:play'"
                width="24"
              ></iconify-icon>
            </button>
            <button
              class="w-12 h-12 rounded-full flex items-center justify-center text-[#CCCCCC] hover:text-[#FFD700] mx-2"
              @click="nextSong"
            >
              <iconify-icon icon="mdi:skip-next" width="30"></iconify-icon>
            </button>
          </div>

          <div class="flex items-center">
            <span class="text-xs text-[#808080]">{{ currentSong.currentTime }}</span>
            <div
              class="flex-1 bg-[#333] h-1 rounded-full overflow-hidden mx-2 cursor-pointer"
              @click="handleProgressClick"
            >
              <div class="bg-[#FFD700] h-full" :style="{ width: currentSong.progress + '%' }"></div>
            </div>
            <span class="text-xs text-[#808080]">{{ currentSong.duration }}</span>
          </div>
        </div>

        <div class="flex items-center">
          <iconify-icon
            class="text-[#CCCCCC] hover:text-[#FFD700] mr-5 cursor-pointer"
            icon="mdi:playlist-music"
            width="24"
          ></iconify-icon>
          <iconify-icon
            class="text-[#CCCCCC] hover:text-[#FFD700] cursor-pointer"
            icon="mdi:volume-high"
            width="24"
          ></iconify-icon>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { usePlayerStore } from '../../store/modules/player'

const playerStore = usePlayerStore()

// 解构状态
const { currentSong } = playerStore

// 解构方法
const { togglePlay, nextSong, prevSong, setProgress } = playerStore

// 处理进度条点击
const handleProgressClick = (e) => {
  const progress = (e.offsetX / e.target.offsetWidth) * 100
  setProgress(progress)
}
</script>

<style scoped>
/* 样式已通过全局Tailwind配置 */
</style>
