import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import Login from "@/views/Login.vue";
import Department from "@/views/Department.vue";
import PersonPage from "@/views/PersonPage.vue";
import Person from "@/views/Person.vue"
import Item from "@/views/Item.vue"
import Form from "@/views/Form.vue";
import Mytest from "@/views/test.vue"

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/home',
      name: 'home',
      component: HomeView,
      children:[
        {
          path: '/Department',
          component: Department,
          meta: { title: 'Department' }
        },
        {
          path: '/Item',
          component: Item,
          meta: { title: 'Item' }
        },
        {
          path: '/Person',
          component: Person,
          meta: { title: 'Person' }
        },
        {
          path: '/PersonPage',
          component: PersonPage,
          meta: { title: 'PersonPage' }
        },
        {
          path: '/Form',
          component: Form,
          meta: { title: 'Form' }
        },
      ]
    },
    {
      path: '/about',
      name: 'about',
      component: () => import('../views/AboutView.vue')
    },
    {
      path: '/',
      name: 'test',
      component: Login

      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      //component: () => import('../views/AboutView.vue')
    },{
      path:'/test',
      name:'Mytest',
      component : Mytest
    }
  ]
})

export default router
