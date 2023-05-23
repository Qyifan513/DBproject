<script  lang="ts">
import { defineComponent, getCurrentInstance, ref, reactive } from "vue";
import {RouterView} from "vue-router";
export default defineComponent({
    setup(){
        const { proxy } = getCurrentInstance()!;
        const activeIndex2 = ref('1')
        const handleSelect = (key: string, keyPath: string[]) => {
            console.log(key, keyPath)
            if(key == "2-1") {
                console.log("!");
                if(proxy) proxy.$router.push('/Department');
            }else if(key == "1"){
                if(proxy) proxy.$router.push('/PersonPage');
            }else if(key == "4") {
                if(proxy) proxy.$router.push('/Form');
            }else if(key == "2-2"){
                if(proxy) proxy.$router.push("/Item");
            }else if(key == "2-3"){
                if(proxy) proxy.$router.push("/Person");
            }
        }
        return{
            activeIndex2,
            handleSelect,
        }
    }
})
</script>
<template>
    <header>
        <div class="h-6" />
        <el-menu
                :default-active="activeIndex2"
                class="el-menu-demo"
                mode="horizontal"
                background-color="#545c64"
                text-color="#fff"
                active-text-color="#ffd04b"
                @select="handleSelect"
        >
            <el-menu-item index="1">首页</el-menu-item>
            <el-menu-item index="2-1">科室管理</el-menu-item>
            <el-menu-item index="2-2">检查项目</el-menu-item>
            <el-menu-item index="2-3">待检人员信息</el-menu-item>
            <!-- <el-sub-menu index="2">
                <template #title>工作中心</template>
                <el-menu-item index="2-1">科室管理</el-menu-item>
                <el-menu-item index="2-2">检查项目</el-menu-item>
                <el-menu-item index="2-3">待检人员信息</el-menu-item>
            </el-sub-menu> -->
<!--            <el-menu-item index="3" disabled>Info</el-menu-item>-->
            <el-menu-item index="4">问题反馈</el-menu-item>
        </el-menu>
    </header>
<body>
<div class="home-body">
    <RouterView />
</div>
</body>
</template>
<style>
.home-body {
    padding-top: 3%;
}
</style>
