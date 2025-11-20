// 引入Vue
import { createApp } from 'vue'
import { createPinia } from 'pinia'
// 引入根组件
import App from './App.vue'
import router from './router'
// 引入图标库
import 'https://code.iconify.design/iconify-icon/1.0.7/iconify-icon.min.js'
import 'https://cdn.jsdelivr.net/npm/echarts@5.4.3/dist/echarts.min.js'
// 引入全局样式
import './assets/styles/main.css'
// 创建Vue, pinia实例
const app = createApp(App)
const pinia = createPinia()

// 配置编译选项，将iconify-icon视为自定义元素
app.config.compilerOptions.isCustomElement = (tag) => tag.startsWith('iconify-icon')

// 挂载pinia实例和路由
app.use(pinia)
app.use(router)
// 挂载根组件
app.mount('#app')
