<template>
  <div class="container py-8">
    <!-- 用户信息区域 -->
    <div v-if="!loading && !error" class="card-bg rounded-xl p-6 mb-8">
      <div class="flex flex-col md:flex-row items-center">
        <!-- 头像 -->
        <div
          class="w-24 h-24 rounded-full overflow-hidden border-4 border-[#FFD700] mb-4 md:mb-0 md:mr-6"
        >
          <img
            :src="profileForm.avatar || defaultAvatar"
            alt="用户头像"
            class="w-full h-full object-cover"
          />
        </div>

        <!-- 用户信息 -->
        <div class="text-center md:text-left flex-1">
          <h2 class="text-2xl font-bold text-white mb-1">{{ profileForm.username }}</h2>
          <p class="text-gray-300 mb-3">{{ profileForm.description || '暂无个人简介' }}</p>

          <div class="flex justify-center md:justify-start space-x-6">
            <div class="text-center">
              <div class="text-white font-bold text-lg">{{ userStats.collectCount }}</div>
              <div class="text-gray-300 text-sm">收藏</div>
            </div>
            <div class="text-center">
              <div class="text-white font-bold text-lg">{{ userStats.commentCount }}</div>
              <div class="text-gray-300 text-sm">评论</div>
            </div>
            <div class="text-center">
              <div class="text-white font-bold text-lg">{{ userStats.scoreCount }}</div>
              <div class="text-gray-300 text-sm">评分</div>
            </div>
            <div class="text-center">
              <div class="text-white font-bold text-lg">{{ userStats.followCount }}</div>
              <div class="text-gray-300 text-sm">关注</div>
            </div>
          </div>
        </div>

        <!-- 编辑按钮 -->
        <div class="mt-4 md:mt-0">
          <button @click="openEditModal" class="main-btn px-4 py-2 rounded-lg flex items-center">
            <iconify-icon icon="mdi:pencil" width="20" class="mr-2"></iconify-icon>
            编辑
          </button>
        </div>
      </div>
    </div>

    <!-- 加载状态 -->
    <div v-if="loading" class="flex justify-center items-center py-16">
      <div class="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-[#FFD700]"></div>
    </div>

    <!-- 错误状态 -->
    <div v-else-if="error" class="text-center py-16">
      <p class="text-[#FF6B6B] mb-4">{{ error }}</p>
      <button @click="fetchUserProfile" class="main-btn px-4 py-2">重试</button>
    </div>

    <!-- 详细信息卡片 -->
    <div v-if="!loading && !error" class="card-bg rounded-xl p-6">
      <h2 class="text-xl font-bold text-white mb-6">详细信息</h2>

      <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
        <div class="flex">
          <div class="w-32 text-gray-400">用户名</div>
          <div class="flex-1 text-white">{{ profileForm.username }}</div>
        </div>

        <div class="flex">
          <div class="w-32 text-gray-400">性别</div>
          <div class="flex-1 text-white">{{ getSexText(profileForm.sex) }}</div>
        </div>

        <div class="flex">
          <div class="w-32 text-gray-400">生日</div>
          <div class="flex-1 text-white">{{ profileForm.birthday || '未设置' }}</div>
        </div>

        <div class="flex">
          <div class="w-32 text-gray-400">注册时间</div>
          <div class="flex-1 text-white">{{ profileForm.createTime || '未知' }}</div>
        </div>

        <div class="md:col-span-2 flex">
          <div class="w-32 text-gray-400">个人简介</div>
          <div class="flex-1 text-white">{{ profileForm.description || '暂无个人简介' }}</div>
        </div>
      </div>
    </div>

    <!-- 安全设置卡片 -->
    <div v-if="!loading && !error" class="card-bg rounded-xl p-6 mt-8">
      <h2 class="text-xl font-bold text-white mb-6">安全设置</h2>

      <div class="space-y-4">
        <div class="flex items-center justify-between p-4 bg-[#1F1F1F] rounded-lg">
          <div>
            <h3 class="font-medium text-white">修改密码</h3>
            <p class="text-sm text-gray-400">定期更改密码以保护账户安全</p>
          </div>
          <button
            @click="showPasswordModal = true"
            class="outline-btn px-4 py-2 rounded-lg text-sm"
          >
            修改
          </button>
        </div>

        <div class="flex items-center justify-between p-4 bg-[#1F1F1F] rounded-lg">
          <div>
            <h3 class="font-medium text-white">绑定社交账号</h3>
            <p class="text-sm text-gray-400">绑定第三方账号以方便登录</p>
          </div>
          <button class="outline-btn px-4 py-2 rounded-lg text-sm">管理</button>
        </div>
      </div>
    </div>

    <!-- 编辑个人信息弹窗 -->
    <div
      v-if="showEditModal"
      class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
      @keydown.esc="closeEditModal"
      @click="onEditModalBackdropClick"
    >
      <div
        class="bg-[#1F1F1F] rounded-xl p-6 w-full max-w-md max-h-[90vh] overflow-y-auto"
        @click.stop
      >
        <div class="flex justify-between items-center mb-4">
          <h3 class="text-xl font-bold text-white">编辑个人信息</h3>
          <button @click="closeEditModal" class="text-gray-400 hover:text-white">
            <iconify-icon icon="mdi:close" width="24"></iconify-icon>
          </button>
        </div>

        <form @submit.prevent="updateProfile">
          <div class="space-y-4 mb-6">
            <!-- 头像上传 -->
            <div class="flex flex-col items-center">
              <div class="w-24 h-24 rounded-full overflow-hidden mb-3">
                <img
                  :src="editForm.avatar || defaultAvatar"
                  alt="用户头像"
                  class="w-full h-full object-cover"
                />
              </div>
              <button
                type="button"
                class="outline-btn px-4 py-1 rounded-lg text-sm"
                @click="triggerAvatarUpload"
              >
                更换头像
              </button>
              <input
                type="file"
                ref="avatarInput"
                class="hidden"
                accept="image/*"
                @change="handleAvatarUpload"
              />
            </div>

            <div>
              <label class="block text-sm font-medium mb-2">用户名</label>
              <input
                v-model="editForm.username"
                type="text"
                class="w-full bg-[#2D2D2D] border border-[#333] rounded-lg py-3 px-4 focus:border-[#FFD700] focus:outline-none text-white"
                placeholder="用户名"
              />
            </div>

            <div>
              <label class="block text-sm font-medium mb-2">性别</label>
              <select
                v-model="editForm.sex"
                class="w-full bg-[#2D2D2D] border border-[#333] rounded-lg py-3 px-4 focus:border-[#FFD700] focus:outline-none text-white"
              >
                <option :value="0">保密</option>
                <option :value="1">男</option>
                <option :value="2">女</option>
              </select>
            </div>

            <div>
              <label class="block text-sm font-medium mb-2">生日</label>
              <input
                v-model="editForm.birthday"
                type="date"
                class="w-full bg-[#2D2D2D] border border-[#333] rounded-lg py-3 px-4 focus:border-[#FFD700] focus:outline-none text-white"
              />
            </div>

            <div>
              <label class="block text-sm font-medium mb-2">个人简介</label>
              <textarea
                v-model="editForm.description"
                rows="3"
                class="w-full bg-[#2D2D2D] border border-[#333] rounded-lg py-3 px-4 focus:border-[#FFD700] focus:outline-none text-white"
                placeholder="个人简介"
              ></textarea>
            </div>
          </div>

          <div class="flex justify-end space-x-3">
            <button type="button" @click="closeEditModal" class="outline-btn px-4 py-2">
              取消
            </button>
            <button
              type="submit"
              :disabled="updateLoading"
              class="main-btn px-4 py-2 flex items-center"
              :class="{ 'opacity-50 cursor-not-allowed': updateLoading }"
            >
              <span v-if="updateLoading">保存中...</span>
              <span v-else>保存更改</span>
            </button>
          </div>
        </form>
      </div>
    </div>

    <!-- 修改密码弹窗 -->
    <div
      v-if="showPasswordModal"
      class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
      @keydown.esc="closePasswordModal"
      @click="onPasswordModalBackdropClick"
    >
      <div class="bg-[#1F1F1F] rounded-xl p-6 w-full max-w-md" @click.stop>
        <div class="flex justify-between items-center mb-4">
          <h3 class="text-xl font-bold text-white">修改密码</h3>
          <button @click="closePasswordModal" class="text-gray-400 hover:text-white">
            <iconify-icon icon="mdi:close" width="24"></iconify-icon>
          </button>
        </div>

        <form @submit.prevent="changePassword">
          <div class="space-y-4 mb-6">
            <div>
              <label class="block text-sm font-medium mb-2">当前密码</label>
              <input
                v-model="passwordForm.currentPassword"
                type="password"
                class="w-full bg-[#2D2D2D] border border-[#333] rounded-lg py-3 px-4 focus:border-[#FFD700] focus:outline-none text-white"
                placeholder="请输入当前密码"
              />
            </div>

            <div>
              <label class="block text-sm font-medium mb-2">新密码</label>
              <input
                v-model="passwordForm.newPassword"
                type="password"
                class="w-full bg-[#2D2D2D] border border-[#333] rounded-lg py-3 px-4 focus:border-[#FFD700] focus:outline-none text-white"
                placeholder="请输入新密码"
              />
            </div>

            <div>
              <label class="block text-sm font-medium mb-2">确认新密码</label>
              <input
                v-model="passwordForm.confirmPassword"
                type="password"
                class="w-full bg-[#2D2D2D] border border-[#333] rounded-lg py-3 px-4 focus:border-[#FFD700] focus:outline-none text-white"
                placeholder="请再次输入新密码"
              />
            </div>
          </div>

          <div
            v-if="passwordMessage"
            class="mb-4 text-center"
            :class="passwordMessage.includes('成功') ? 'text-green-500' : 'text-red-500'"
          >
            {{ passwordMessage }}
          </div>

          <div class="flex justify-end space-x-3">
            <button type="button" @click="closePasswordModal" class="outline-btn px-4 py-2">
              取消
            </button>
            <button
              type="submit"
              :disabled="passwordLoading"
              class="main-btn px-4 py-2 flex items-center"
              :class="{ 'opacity-50 cursor-not-allowed': passwordLoading }"
            >
              <span v-if="passwordLoading">提交中...</span>
              <span v-else>确认修改</span>
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { userApi } from '@/api'

const router = useRouter()

// 用户信息
const profileForm = reactive({
  username: '',
  sex: 0,
  avatar: '',
  description: '',
  birthday: '',
  createTime: '',
})

// 编辑表单
const editForm = reactive({
  username: '',
  sex: 0,
  avatar: '',
  description: '',
  birthday: '',
})

// 密码表单
const passwordForm = reactive({
  currentPassword: '',
  newPassword: '',
  confirmPassword: '',
})

// 用户统计数据
const userStats = reactive({
  collectCount: 128,
  commentCount: 42,
  scoreCount: 65,
  followCount: 24,
})

// 状态管理
const loading = ref(true)
const updateLoading = ref(false)
const passwordLoading = ref(false)
const error = ref(null)
const passwordMessage = ref('')
const showEditModal = ref(false)
const showPasswordModal = ref(false)

// 默认头像
const defaultAvatar = 'https://via.placeholder.com/150x150/333/888?text=头像'

// 头像上传相关
const avatarInput = ref(null)

// 原始用户信息（用于取消编辑时恢复）
const originalProfile = reactive({})

// 获取性别文本
const getSexText = (sex) => {
  switch (sex) {
    case 1:
      return '男'
    case 2:
      return '女'
    default:
      return '保密'
  }
}

// 获取用户信息
const fetchUserProfile = async () => {
  try {
    loading.value = true
    error.value = null

    // 检查用户是否已登录
    const token = localStorage.getItem('authentication')
    const userId = localStorage.getItem('userId')

    if (!token || !userId) {
      router.push('/auth')
      return
    }

    const response = await userApi.getUserProfile(userId)

    if (response.code === 200) {
      const userData = response.data

      // 填充表单数据
      profileForm.username = userData.username || ''
      profileForm.sex = userData.sex || 0
      profileForm.avatar = userData.avatar || ''
      profileForm.description = userData.description || ''
      profileForm.birthday = userData.birthday ? userData.birthday.split('T')[0] : ''
      profileForm.createTime = userData.createTime
        ? new Date(userData.createTime).toLocaleDateString()
        : ''

      // 保存原始数据
      Object.assign(originalProfile, profileForm)
    } else {
      throw new Error(response.message || '获取用户信息失败')
    }
  } catch (err) {
    console.error('获取用户信息失败:', err)
    error.value = '获取用户信息失败，请稍后重试'

    // 如果是认证错误，跳转到登录页
    if (err.code === 401) {
      localStorage.removeItem('authentication')
      router.push('/auth')
    }
  } finally {
    loading.value = false
  }
}

// 打开编辑弹窗
const openEditModal = () => {
  // 将当前用户信息复制到编辑表单
  Object.assign(editForm, profileForm)
  showEditModal.value = true
}

// 关闭编辑弹窗
const closeEditModal = () => {
  showEditModal.value = false
}

// 点击遮罩层关闭编辑弹窗
const onEditModalBackdropClick = (event) => {
  if (event.target === event.currentTarget) {
    closeEditModal()
  }
}

// 触发头像上传
const triggerAvatarUpload = () => {
  avatarInput.value.click()
}

// 处理头像上传
const handleAvatarUpload = (event) => {
  const file = event.target.files[0]
  if (file) {
    // 在实际应用中，这里会上传文件到服务器
    // 模拟上传成功并更新头像URL
    const reader = new FileReader()
    reader.onload = (e) => {
      editForm.avatar = e.target.result
    }
    reader.readAsDataURL(file)

    // 重置input，以便下次选择同一文件也能触发change事件
    event.target.value = ''
  }
}

// 更新用户信息
const updateProfile = async () => {
  try {
    updateLoading.value = true

    // 在实际应用中，这里会调用API更新用户信息
    // 模拟API调用
    await new Promise((resolve) => setTimeout(resolve, 1000))

    // 更新成功，保存新的数据
    Object.assign(profileForm, editForm)
    closeEditModal()

    // 显示成功消息（可以添加一个全局提示组件）
    alert('个人信息更新成功')
  } catch (err) {
    console.error('更新用户信息失败:', err)
    alert(err.message || '更新失败，请稍后重试')
  } finally {
    updateLoading.value = false
  }
}

// 修改密码
const changePassword = async () => {
  // 基本验证
  if (!passwordForm.currentPassword || !passwordForm.newPassword || !passwordForm.confirmPassword) {
    passwordMessage.value = '请填写所有密码字段'
    return
  }

  if (passwordForm.newPassword !== passwordForm.confirmPassword) {
    passwordMessage.value = '新密码与确认密码不一致'
    return
  }

  if (passwordForm.newPassword.length < 6) {
    passwordMessage.value = '新密码长度不能少于6位'
    return
  }

  try {
    passwordLoading.value = true
    passwordMessage.value = ''

    // 在实际应用中，这里会调用API修改密码
    // 模拟API调用
    await new Promise((resolve) => setTimeout(resolve, 1000))

    passwordMessage.value = '密码修改成功'

    // 清空表单
    passwordForm.currentPassword = ''
    passwordForm.newPassword = ''
    passwordForm.confirmPassword = ''

    // 2秒后关闭弹窗
    setTimeout(() => {
      closePasswordModal()
    }, 2000)
  } catch (err) {
    console.error('修改密码失败:', err)
    passwordMessage.value = err.message || '密码修改失败，请稍后重试'
  } finally {
    passwordLoading.value = false
  }
}

// 关闭密码弹窗
const closePasswordModal = () => {
  showPasswordModal.value = false
  passwordMessage.value = ''

  // 清空表单
  passwordForm.currentPassword = ''
  passwordForm.newPassword = ''
  passwordForm.confirmPassword = ''
}

// 点击遮罩层关闭密码弹窗
const onPasswordModalBackdropClick = (event) => {
  if (event.target === event.currentTarget) {
    closePasswordModal()
  }
}

onMounted(() => {
  fetchUserProfile()
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

/* 弹窗背景可点击关闭 */
.fixed.inset-0.bg-black.bg-opacity-50 {
  cursor: pointer;
}
</style>
