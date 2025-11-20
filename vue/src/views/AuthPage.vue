<template>
  <div class="container py-16 pt-24">
    <!-- 登录成功弹窗 -->
    <div
      v-if="showSuccessModal"
      class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
    >
      <div class="bg-[#1F1F1F] rounded-lg p-6 max-w-md w-full mx-4">
        <div class="text-center">
          <div
            class="w-16 h-16 bg-green-500 rounded-full flex items-center justify-center mx-auto mb-4"
          >
            <iconify-icon icon="mdi:check" width="32" class="text-white"></iconify-icon>
          </div>
          <h3 class="text-xl font-bold mb-2">{{ successTitle }}</h3>
          <p class="text-[#808080] mb-6">{{ successMessage }}</p>
        </div>
      </div>
    </div>

    <!-- 错误弹窗 -->
    <div
      v-if="showErrorModal"
      class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
    >
      <div class="bg-[#1F1F1F] rounded-lg p-6 max-w-md w-full mx-4">
        <div class="text-center">
          <div
            class="w-16 h-16 bg-red-500 rounded-full flex items-center justify-center mx-auto mb-4"
          >
            <iconify-icon icon="mdi:close" width="32" class="text-white"></iconify-icon>
          </div>
          <h3 class="text-xl font-bold mb-2">操作失败</h3>
          <p class="text-[#808080] mb-6">{{ errorMessage }}</p>
          <button @click="closeErrorModal" class="main-btn px-6 py-2 rounded-lg">确定</button>
        </div>
      </div>
    </div>

    <div class="max-w-md mx-auto card-bg p-8 rounded-xl">
      <!-- 标签切换 -->
      <div class="flex mb-8">
        <button
          class="flex-1 py-3 text-center font-medium text-lg"
          :class="isLogin ? 'border-b-2 border-[#FFD700] text-white' : 'text-[#808080]'"
          @click="isLogin = true"
        >
          登录
        </button>
        <button
          class="flex-1 py-3 text-center font-medium text-lg"
          :class="!isLogin ? 'border-b-2 border-[#FFD700] text-white' : 'text-[#808080]'"
          @click="isLogin = false"
        >
          注册
        </button>
      </div>

      <!-- 登录表单 -->
      <form v-if="isLogin" class="space-y-6">
        <div>
          <label class="block text-sm font-medium mb-2">用户名/邮箱</label>
          <input
            v-model="loginForm.account"
            type="text"
            class="w-full bg-[#2D2D2D] border border-[#333] rounded-lg py-3 px-4 focus:border-[#FFD700] focus:outline-none"
            placeholder="请输入用户名或邮箱"
          />
        </div>

        <div>
          <label class="block text-sm font-medium mb-2">密码</label>
          <input
            v-model="loginForm.password"
            type="password"
            class="w-full bg-[#2D2D2D] border border-[#333] rounded-lg py-3 px-4 focus:border-[#FFD700] focus:outline-none"
            placeholder="请输入密码"
          />
        </div>

        <div class="flex items-center justify-between">
          <div class="flex items-center">
            <input
              type="checkbox"
              id="remember"
              class="rounded bg-[#2D2D2D] border-[#333] focus:ring-[#FFD700] focus:border-[#FFD700]"
            />
            <label for="remember" class="ml-2 text-sm">记住我</label>
          </div>
          <a href="#" class="text-sm text-[#FFD700] hover:underline">忘记密码？</a>
        </div>

        <button
          type="button"
          @click="handleLogin"
          class="w-full main-btn rounded-lg py-3 font-medium hover:scale-105"
          :disabled="loginLoading"
        >
          {{ loginLoading ? '登录中...' : '登录' }}
        </button>
      </form>

      <!-- 注册表单 -->
      <form v-else class="space-y-6">
        <div>
          <label class="block text-sm font-medium mb-2">用户名</label>
          <input
            v-model="registerForm.username"
            type="text"
            class="w-full bg-[#2D2D2D] border border-[#333] rounded-lg py-3 px-4 focus:border-[#FFD700] focus:outline-none"
            placeholder="请输入用户名"
          />
        </div>

        <div>
          <label class="block text-sm font-medium mb-2">手机号</label>
          <input
            v-model="registerForm.phone"
            type="tel"
            class="w-full bg-[#2D2D2D] border border-[#333] rounded-lg py-3 px-4 focus:border-[#FFD700] focus:outline-none"
            placeholder="请输入手机号"
          />
        </div>

        <div>
          <label class="block text-sm font-medium mb-2">邮箱</label>
          <input
            v-model="registerForm.email"
            type="email"
            class="w-full bg-[#2D2D2D] border border-[#333] rounded-lg py-3 px-4 focus:border-[#FFD700] focus:outline-none"
            placeholder="请输入邮箱地址"
          />
        </div>

        <div>
          <label class="block text-sm font-medium mb-2">密码</label>
          <input
            v-model="registerForm.password"
            type="password"
            class="w-full bg-[#2D2D2D] border border-[#333] rounded-lg py-3 px-4 focus:border-[#FFD700] focus:outline-none"
            placeholder="请输入密码"
          />
        </div>

        <div>
          <label class="block text-sm font-medium mb-2">确认密码</label>
          <input
            v-model="registerForm.confirmPassword"
            type="password"
            class="w-full bg-[#2D2D2D] border border-[#333] rounded-lg py-3 px-4 focus:border-[#FFD700] focus:outline-none"
            placeholder="请再次输入密码"
          />
        </div>

        <div class="flex items-center">
          <input
            type="checkbox"
            id="agree"
            v-model="agreedToTerms"
            class="rounded bg-[#2D2D2D] border-[#333] focus:ring-[#FFD700] focus:border-[#FFD700]"
          />
          <label for="agree" class="ml-2 text-sm"
            >我已阅读并同意 <a href="#" class="text-[#FFD700] hover:underline">用户协议</a> 和
            <a href="#" class="text-[#FFD700] hover:underline">隐私政策</a></label
          >
        </div>

        <button
          type="button"
          @click="handleRegister"
          class="w-full main-btn rounded-lg py-3 font-medium hover:scale-105"
          :disabled="registerLoading"
        >
          {{ registerLoading ? '注册中...' : '注册' }}
        </button>
      </form>

      <!-- 社交登录 -->
      <div class="mt-8">
        <div class="relative">
          <div class="absolute inset-0 flex items-center">
            <div class="w-full border-t border-[#333]"></div>
          </div>
          <div class="relative flex justify-center text-sm">
            <span class="px-2 bg-[#1F1F1F] text-[#808080]">其他登录方式</span>
          </div>
        </div>

        <div class="mt-6 flex justify-center space-x-4">
          <button
            class="w-12 h-12 rounded-full bg-[#1F1F1F] flex items-center justify-center hover:bg-[#2D2D2D]"
          >
            <iconify-icon icon="mdi:google" width="24" class="text-white"></iconify-icon>
          </button>
          <button
            class="w-12 h-12 rounded-full bg-[#1F1F1F] flex items-center justify-center hover:bg-[#2D2D2D]"
          >
            <iconify-icon icon="mdi:facebook" width="24" class="text-white"></iconify-icon>
          </button>
          <button
            class="w-12 h-12 rounded-full bg-[#1F1F1F] flex items-center justify-center hover:bg-[#2D2D2D]"
          >
            <iconify-icon icon="mdi:twitter" width="24" class="text-white"></iconify-icon>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { login, register } from '@/services/userService.js'
import { useAuthStore } from '@/services/api.js'

const isLogin = ref(true)
const router = useRouter()
const agreedToTerms = ref(false)

// 弹窗相关状态
const showSuccessModal = ref(false)
const showErrorModal = ref(false)
const errorMessage = ref('')
const successTitle = ref('')
const successMessage = ref('')

const loginForm = reactive({
  account: '',
  password: '',
})

const registerForm = reactive({
  username: '',
  phone: '',
  email: '',
  password: '',
  confirmPassword: '',
})

// 登录状态和消息
const loginLoading = ref(false)
const loginMessage = ref('')
const loginSuccess = ref(false)

// 注册状态和消息
const registerLoading = ref(false)
const registerMessage = ref('')
const registerSuccess = ref(false)

const authStore = useAuthStore()

// 关闭错误弹窗
const closeErrorModal = () => {
  showErrorModal.value = false
  errorMessage.value = ''
}

const handleLogin = async () => {
  // 重置消息
  loginMessage.value = ''
  loginSuccess.value = false

  // 基本验证
  if (!loginForm.account || !loginForm.password) {
    errorMessage.value = '请输入账号和密码'
    showErrorModal.value = true
    return
  }

  try {
    loginLoading.value = true
    const response = await login(loginForm)

    // 根据接口文档，状态码为200才是响应成功
    if (response.code === 200) {
      loginSuccess.value = true
      successTitle.value = '登录成功'
      successMessage.value = '欢迎回来，正在跳转到首页...'

      // 存储用户ID和token到localStorage
      if (response.data) {
        if (response.data.id) {
          localStorage.setItem('userId', response.data.id)
        }
        if (response.data.username) {
          localStorage.setItem('username', response.data.username)
        }
        if (response.data.token) {
          authStore.setToken(response.data.token)
        }
      }

      // 延迟跳转，让用户看到成功消息
      setTimeout(() => {
        router.push('/')
      }, 1500)
    } else {
      // 状态码不是200，表示登录失败
      loginSuccess.value = false
      errorMessage.value = response.message || '登录失败，请检查账号和密码'
      showErrorModal.value = true
    }
  } catch (error) {
    loginSuccess.value = false
    errorMessage.value = error.message || '登录失败，请检查账号和密码'
    showErrorModal.value = true
    console.error('登录失败:', error)
  } finally {
    loginLoading.value = false
  }
}

const handleRegister = async () => {
  // 重置消息
  registerMessage.value = ''
  registerSuccess.value = false

  // 基本验证
  if (
    !registerForm.username ||
    (!registerForm.phone && !registerForm.email) ||
    !registerForm.password
  ) {
    errorMessage.value = '请填写所有必填字段'
    showErrorModal.value = true
    return
  }

  if (registerForm.password !== registerForm.confirmPassword) {
    errorMessage.value = '两次输入的密码不一致'
    showErrorModal.value = true
    return
  }

  if (!agreedToTerms.value) {
    errorMessage.value = '请同意用户协议和隐私政策'
    showErrorModal.value = true
    return
  }

  try {
    registerLoading.value = true
    const response = await register(registerForm)

    // 根据接口文档，状态码为200才是响应成功
    if (response.code === 200) {
      registerSuccess.value = true

      // 注册成功后直接使用返回的用户ID和token进行登录
      // 存储用户ID和token到localStorage
      if (response.data) {
        if (response.data.id) {
          localStorage.setItem('userId', response.data.id)
        }
        if (response.data.username) {
          localStorage.setItem('username', response.data.username)
        }
        if (response.data.token) {
          authStore.setToken(response.data.token)
        }
      }

      // 显示成功弹窗并跳转
      successTitle.value = '注册并登录成功'
      successMessage.value = '正在跳转到首页...'
      showSuccessModal.value = true

      setTimeout(() => {
        router.push('/')
      }, 1500)
    } else {
      // 注册失败
      registerSuccess.value = false
      errorMessage.value = response.message || '注册失败'
      showErrorModal.value = true
    }
  } catch (error) {
    registerSuccess.value = false
    errorMessage.value = error.message || '注册失败'
    showErrorModal.value = true
    console.error('注册失败:', error)
  } finally {
    registerLoading.value = false
  }
}
</script>

<style scoped>
/* 样式已通过全局Tailwind配置 */
</style>
