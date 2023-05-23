<template>
    <div id="test">
        这是测试页面
        {{name}}
        <button @click="useMouse">{{ y }}Greet{{ x }}</button>
    </div>
    <el-button class="login-btn" type="primary">登录</el-button>
    <!-- 使用特殊的 $event 变量 -->
<!-- 使用内联箭头函数 -->
<button @click="(event) => warn('Form cannot be submitted yet.')">
  Submit
</button>
</template>
    
<script lang="ts">
import { xor } from 'lodash'
import { onMounted, ref } from 'vue'

export default {
    data() {
        return {
            name: 'test',
            x : '0',
            y : '0'
        }
    },
    methods: {
       warn(message : string){
        alert(message);
       },
        greet() {
            // `this` inside methods points to the current active instance
            alert(`Hello ${this.name}!`)
        },
        useMouse() {
	let x = ref(0)
	let y = ref(0)

	onMounted(() => {
		window.addEventListener('mousemove', e => {
			x.value = e.pageX
			y.value = e.pageY
		})
	})
	return {x, y};//x,y可以在组件中使用
}
    },

}
</script>
    
<style scoped></style>