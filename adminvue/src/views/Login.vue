<template>
    <body>
    <div class="login-container">
        <div class="title">后台管理</div>
        <div class="login">
            <el-form :model="ruleForm" :rules="rules">
                <el-form-item prop="username">
                    <el-input v-model="ruleForm.username" placeholder="username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input type="password" placeholder="password" v-model="ruleForm.password" @keyup.enter="submitForm"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button class="login-btn" type="primary" @click="submitForm">登录</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
    </body>

</template>

<script lang="ts">
import {defineComponent, getCurrentInstance, ref, reactive} from "vue";

// import mixin from "@/mixins/mixin";
 import { HttpManager } from "@/api/index";
// import { RouterName, MUSICNAME } from "@/enums";
const nusicName = 'Yin-music 后台管理'
export default defineComponent({
  setup() {

    const { proxy } = getCurrentInstance()!;
    const ruleForm = reactive({
      username: "admin",
      password: "123",
    });
    const rules = reactive({
      username: [{ required: true, message: "请输入用户名", trigger: "blur" }],
      password: [{ required: true, message: "请输入密码", trigger: "blur" }],
    });
    async function submitForm() {
      let params = new URLSearchParams();
      params.append("name", ruleForm.username);
      params.append("password", ruleForm.password);
      console.log(params);
      console.log({name : ruleForm.username,password:ruleForm.password});
      //如果账号密码正确则跳转到管理员页面
      HttpManager.getLoginStatus({name : ruleForm.username,password:ruleForm.password}).then(resp=>{
        console.log(resp);
        console.log(resp.success);
        if(resp.success == true){
          if(proxy) proxy.$router.push('/home')
        }else{
          alert(`账号或密码错误!`)
        }
      })
      //const result = (await HttpManager.getLoginStatus(params)) as ResponseBody;
    }
    return {
      ruleForm,
      rules,
      submitForm,
    };
  },
});
</script>

<style scoped>
.login-container {
  position: relative;
    background: #95d2f6;
    /*background: url("../assets/images/background.jpg") fixed center;*/
    /*background-size: cover;*/
  width: 100%;
  height: 100%;
}

.title {
  position: absolute;
  top: 50%;
  width: 100%;
  margin-top: -230px;
  text-align: center;
  font-size: 30px;
  font-weight: 600;
  color: rgb(92, 114, 226);
}

.login {
  position: absolute;
  left: 52%;
  top: 50%;
  width: 300px;
  margin: -150px 0 0 -190px;
  padding: 40px;
  border-radius: 5px;
  background: #a50707;
}

.login-btn {
  width: 100%;
}
</style>
