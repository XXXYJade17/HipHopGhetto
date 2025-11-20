import { createRouter, createWebHistory } from 'vue-router'
import AuthPage from '../views/AuthPage.vue'
import HomePage from '../views/HomePage.vue'
import DiscoverPage from '../views/DiscoverPage.vue'
import AlbumDetailPage from '../views/AlbumDetailPage.vue'
import SongDetailPage from '../views/SongDetailPage.vue'
import AlbumCommentsPage from '../views/AlbumCommentsPage.vue'
import SongCommentsPage from '../views/SongCommentsPage.vue'
import ProfilePage from '../views/ProfilePage.vue'
import ForumPage from '../views/ForumPage.vue'
import ForumPostPage from '../views/ForumPostPage.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: HomePage,
    meta: {
      title: '首页',
    },
  },
  {
    path: '/auth',
    name: 'Auth',
    component: AuthPage,
    meta: {
      title: '用户认证',
      hidePlayer: true,
    },
  },
  {
    path: '/discover',
    name: 'Discover',
    component: DiscoverPage,
    meta: {
      title: '发现',
    },
  },
  {
    path: '/forum',
    name: 'Forum',
    component: ForumPage,
    meta: {
      title: '讨论区',
    },
  },
  {
    path: '/forum/post/:id',
    name: 'ForumPost',
    component: ForumPostPage,
    meta: {
      title: '话题详情',
    },
    props: true,
  },
  {
    path: '/album/:id',
    name: 'AlbumDetail',
    component: AlbumDetailPage,
    meta: {
      title: '专辑详情',
    },
    props: true,
  },
  {
    path: '/album/:id/comments',
    name: 'AlbumComments',
    component: AlbumCommentsPage,
    meta: {
      title: '专辑评论',
    },
    props: true,
  },
  {
    path: '/song/:id',
    name: 'SongDetail',
    component: SongDetailPage,
    meta: {
      title: '歌曲详情',
    },
    props: true,
  },
  {
    path: '/song/:id/comments',
    name: 'SongComments',
    component: SongCommentsPage,
    meta: {
      title: '歌曲评论',
    },
    props: true,
  },
  {
    path: '/profile',
    name: 'Profile',
    component: ProfilePage,
    meta: {
      title: '我的账户',
    },
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

// 路由守卫设置页面标题
router.beforeEach((to, from, next) => {
  if (to.meta.title) {
    document.title = to.meta.title
  }
  next()
})

export default router
