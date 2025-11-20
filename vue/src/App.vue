<template>
  <div class="min-h-screen bg-[#0A0A0A] text-[#CCCCCC]">
    <Header />
    <main class="pb-32">
      <!-- 留出播放器空间 -->
      <RouterView />
    </main>
    <Footer />
    <MusicPlayer v-if="!$route.meta.hidePlayer" />
  </div>
</template>

<script setup>
import Header from './components/common/Header.vue'
import Footer from './components/common/Footer.vue'
import MusicPlayer from './components/common/MusicPlayer.vue'
</script>

<style scoped>
/* 全局样式已在main.css中引入 */
</style>
