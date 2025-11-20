import { defineStore } from 'pinia'

export const usePlayerStore = defineStore('player', {
  state: () => ({
    currentSong: {
      title: 'BEAT THE STREET',
      artist: 'Q-BOOM',
      album: 'NEW TRACK',
      cover: '/generated_images/6b7ea6f1dd4e4f579c47a8c47e494b33.jpg',
      duration: '3:12',
      currentTime: '1:36',
      progress: 50
    },
    isPlaying: false,
    volume: 80
  }),
  actions: {
    togglePlay() {
      this.isPlaying = !this.isPlaying
    },
    setProgress(progress) {
      this.currentSong.progress = progress
      // 这里可以添加更新当前播放时间的逻辑
    },
    nextSong() {
      // 下一首歌逻辑
    },
    prevSong() {
      // 上一首歌逻辑
    }
  }
})
