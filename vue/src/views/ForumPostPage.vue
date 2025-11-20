<template>
  <div class="forum-post-page">
    <!-- 返回按钮 -->
    <div class="container mx-auto px-4 py-6">
      <button
        @click="$router.back()"
        class="flex items-center text-blue-500 hover:text-blue-700 mb-6"
      >
        <i class="fas fa-arrow-left mr-2"></i> 返回
      </button>

      <!-- 帖子内容 -->
      <div class="bg-white rounded-lg shadow-md overflow-hidden mb-8">
        <div class="p-6">
          <div class="flex items-start mb-4">
            <img
              :src="post.author.avatar"
              :alt="post.author.name"
              class="w-12 h-12 rounded-full mr-4"
            />
            <div>
              <h1 class="text-2xl font-bold text-gray-800 mb-2">{{ post.title }}</h1>
              <div class="flex items-center text-sm text-gray-500">
                <span class="font-medium">{{ post.author.name }}</span>
                <span class="mx-2">•</span>
                <span>{{ post.publishTime }}</span>
                <span class="mx-2">•</span>
                <span>{{ post.viewCount }} 浏览</span>
              </div>
            </div>
          </div>

          <div class="prose max-w-none mb-6">
            <p>{{ post.content }}</p>
          </div>

          <div class="flex items-center justify-between pt-4 border-t border-gray-200">
            <div class="flex space-x-4">
              <button
                @click="likePost"
                :class="[
                  'flex items-center',
                  post.isLiked ? 'text-red-500' : 'text-gray-500 hover:text-red-500',
                ]"
              >
                <i class="fas fa-heart mr-1"></i>
                <span>{{ post.likeCount }}</span>
              </button>
              <button class="flex items-center text-gray-500 hover:text-blue-500">
                <i class="fas fa-share-alt mr-1"></i>
                <span>分享</span>
              </button>
            </div>

            <div class="text-sm text-gray-500">
              <span>{{ post.replyCount }} 条回复</span>
            </div>
          </div>
        </div>
      </div>

      <!-- 回复列表 -->
      <div class="bg-white rounded-lg shadow-md overflow-hidden mb-8">
        <div class="border-b border-gray-200 px-6 py-4">
          <h2 class="text-lg font-semibold">回复 ({{ post.replyCount }})</h2>
        </div>

        <div class="divide-y divide-gray-200">
          <div v-for="reply in post.replies" :key="reply.id" class="p-6">
            <div class="flex items-start">
              <img
                :src="reply.author.avatar"
                :alt="reply.author.name"
                class="w-10 h-10 rounded-full mr-4"
              />
              <div class="flex-1">
                <div class="flex items-center mb-1">
                  <span class="font-medium text-gray-900">{{ reply.author.name }}</span>
                  <span class="mx-2 text-gray-300">•</span>
                  <span class="text-sm text-gray-500">{{ reply.replyTime }}</span>
                </div>
                <p class="text-gray-700 mb-3">{{ reply.content }}</p>
                <div class="flex items-center">
                  <button
                    @click="likeReply(reply.id)"
                    :class="[
                      'flex items-center text-xs',
                      reply.isLiked ? 'text-red-500' : 'text-gray-500 hover:text-red-500',
                    ]"
                  >
                    <i class="fas fa-heart mr-1"></i>
                    <span>{{ reply.likeCount }}</span>
                  </button>
                  <button
                    @click="showReplyForm(reply.id)"
                    class="ml-4 flex items-center text-xs text-gray-500 hover:text-blue-500"
                  >
                    <i class="fas fa-reply mr-1"></i>
                    <span>回复</span>
                  </button>
                </div>

                <!-- 回复的回复 -->
                <div
                  v-for="subReply in reply.subReplies"
                  :key="subReply.id"
                  class="ml-8 mt-4 pt-4 border-t border-gray-100"
                >
                  <div class="flex items-start">
                    <img
                      :src="subReply.author.avatar"
                      :alt="subReply.author.name"
                      class="w-8 h-8 rounded-full mr-3"
                    />
                    <div class="flex-1">
                      <div class="flex items-center mb-1">
                        <span class="font-medium text-gray-900 text-sm">{{
                          subReply.author.name
                        }}</span>
                        <span class="mx-2 text-gray-300">•</span>
                        <span class="text-xs text-gray-500">{{ subReply.replyTime }}</span>
                      </div>
                      <p class="text-gray-700 text-sm mb-2">{{ subReply.content }}</p>
                      <button
                        @click="likeReply(subReply.id)"
                        :class="[
                          'flex items-center text-xs',
                          subReply.isLiked ? 'text-red-500' : 'text-gray-500 hover:text-red-500',
                        ]"
                      >
                        <i class="fas fa-heart mr-1"></i>
                        <span>{{ subReply.likeCount }}</span>
                      </button>
                    </div>
                  </div>
                </div>

                <!-- 回复表单 -->
                <div v-if="replyingTo === reply.id" class="mt-4">
                  <div class="flex items-start">
                    <img
                      :src="currentUser.avatar"
                      :alt="currentUser.name"
                      class="w-8 h-8 rounded-full mr-3"
                    />
                    <div class="flex-1">
                      <textarea
                        v-model="replyContent"
                        class="w-full border border-gray-300 rounded-lg p-3 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
                        rows="3"
                        placeholder="写下你的回复..."
                      ></textarea>
                      <div class="mt-2 flex justify-end space-x-2">
                        <button
                          @click="cancelReply"
                          class="px-3 py-1 text-sm text-gray-600 hover:bg-gray-100 rounded-lg"
                        >
                          取消
                        </button>
                        <button
                          @click="submitReply(reply.id)"
                          class="px-3 py-1 bg-blue-500 text-white text-sm rounded-lg hover:bg-blue-600"
                        >
                          发布
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 发表回复 -->
      <div class="bg-white rounded-lg shadow-md overflow-hidden">
        <div class="border-b border-gray-200 px-6 py-4">
          <h2 class="text-lg font-semibold">发表回复</h2>
        </div>
        <div class="p-6">
          <div class="flex items-start">
            <img
              :src="currentUser.avatar"
              :alt="currentUser.name"
              class="w-10 h-10 rounded-full mr-4"
            />
            <div class="flex-1">
              <textarea
                v-model="newReplyContent"
                class="w-full border border-gray-300 rounded-lg p-3 focus:outline-none focus:ring-2 focus:ring-blue-500"
                rows="4"
                placeholder="写下你的回复..."
              ></textarea>
              <div class="mt-3 flex justify-end">
                <button
                  @click="submitNewReply"
                  class="px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600"
                >
                  发布回复
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'

export default {
  name: 'ForumPostPage',
  setup() {
    const route = useRoute()
    const postId = route.params.id
    const replyingTo = ref(null)
    const replyContent = ref('')
    const newReplyContent = ref('')

    // 模拟当前用户数据
    const currentUser = ref({
      name: 'CurrentUser',
      avatar: 'https://via.placeholder.com/150',
    })

    // 模拟帖子数据
    const post = ref({
      id: 1,
      title: '如何提高说唱技巧？',
      content:
        '作为一个新手，我想知道如何快速提升自己的说唱技能，有什么好的建议吗？我最近刚开始学习说唱，发现韵脚和节拍的配合非常困难，尤其是如何在保持流畅性的同时还能表达清楚自己的意思。希望有经验的朋友可以分享一些练习方法和技巧，谢谢！',
      author: {
        name: 'RapKing',
        avatar: 'https://via.placeholder.com/150',
      },
      publishTime: '2023-05-15 14:30',
      viewCount: 128,
      likeCount: 24,
      isLiked: false,
      replyCount: 5,
      replies: [
        {
          id: 1,
          content: '我觉得最重要的是多听多练，多模仿一些你喜欢的rapper，学习他们的flow和韵脚。',
          author: {
            name: 'HipHopFan',
            avatar: 'https://via.placeholder.com/150',
          },
          replyTime: '2023-05-15 15:45',
          likeCount: 8,
          isLiked: false,
          subReplies: [
            {
              id: 3,
              content: '确实，模仿是学习的第一步，但也要注意发展自己的风格。',
              author: {
                name: 'MCFlow',
                avatar: 'https://via.placeholder.com/150',
              },
              replyTime: '2023-05-15 16:20',
              likeCount: 3,
              isLiked: true,
            },
          ],
        },
        {
          id: 2,
          content: '推荐你先从写词开始，内容比技巧更重要。内容打动人心，技巧只是表达方式。',
          author: {
            name: 'LyricMaster',
            avatar: 'https://via.placeholder.com/150',
          },
          replyTime: '2023-05-15 16:10',
          likeCount: 12,
          isLiked: true,
          subReplies: [],
        },
      ],
    })

    // 点赞帖子
    const likePost = () => {
      post.value.isLiked = !post.value.isLiked
      post.value.likeCount += post.value.isLiked ? 1 : -1
    }

    // 点赞回复
    const likeReply = (replyId) => {
      const findAndLike = (replies) => {
        for (const reply of replies) {
          if (reply.id === replyId) {
            reply.isLiked = !reply.isLiked
            reply.likeCount += reply.isLiked ? 1 : -1
            return true
          }
          if (reply.subReplies && findAndLike(reply.subReplies)) {
            return true
          }
        }
        return false
      }

      findAndLike(post.value.replies)
    }

    // 显示回复表单
    const showReplyForm = (replyId) => {
      replyingTo.value = replyId
      replyContent.value = ''
    }

    // 取消回复
    const cancelReply = () => {
      replyingTo.value = null
      replyContent.value = ''
    }

    // 提交回复
    const submitReply = (parentId) => {
      if (!replyContent.value.trim()) {
        alert('请输入回复内容')
        return
      }

      // 找到父回复并添加子回复
      const findAndAddReply = (replies) => {
        for (const reply of replies) {
          if (reply.id === parentId) {
            if (!reply.subReplies) {
              reply.subReplies = []
            }
            reply.subReplies.push({
              id: Date.now(), // 简单生成ID
              content: replyContent.value,
              author: {
                name: currentUser.value.name,
                avatar: currentUser.value.avatar,
              },
              replyTime: '刚刚',
              likeCount: 0,
              isLiked: false,
            })
            post.value.replyCount++
            return true
          }
          if (reply.subReplies && findAndAddReply(reply.subReplies)) {
            return true
          }
        }
        return false
      }

      findAndAddReply(post.value.replies)
      replyingTo.value = null
      replyContent.value = ''
    }

    // 提交新回复
    const submitNewReply = () => {
      if (!newReplyContent.value.trim()) {
        alert('请输入回复内容')
        return
      }

      post.value.replies.push({
        id: Date.now(), // 简单生成ID
        content: newReplyContent.value,
        author: {
          name: currentUser.value.name,
          avatar: currentUser.value.avatar,
        },
        replyTime: '刚刚',
        likeCount: 0,
        isLiked: false,
        subReplies: [],
      })

      post.value.replyCount++
      newReplyContent.value = ''
    }

    onMounted(() => {
      // 实际项目中这里会根据postId获取帖子详情
      console.log('加载帖子ID:', postId)
    })

    return {
      post,
      currentUser,
      replyingTo,
      replyContent,
      newReplyContent,
      likePost,
      likeReply,
      showReplyForm,
      cancelReply,
      submitReply,
      submitNewReply,
    }
  },
}
</script>

<style scoped>
.prose {
  color: #374151;
}

.prose p {
  margin-bottom: 1rem;
  line-height: 1.6;
}
</style>
