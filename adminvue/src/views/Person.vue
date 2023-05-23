<template>
    <el-table
            ref="multipleTableRef"
            :data="tableData"
            style="width: 100%"
            @selection-change="handleSelectionChange"
    >
        <el-table-column type="selection" width="55" />
        <el-table-column label="id" width="120">
            <template #default="scope">{{ scope.row.personId }}</template>
        </el-table-column>
        <el-table-column property="name" label="姓名" width="120" />

        <el-table-column property="sex" label="性别" width="250" />
        <el-table-column property="tel" label="联系方式"  width="120"/>
    </el-table>
    <div style="margin-top: 20px">
        <el-button @click="toggleSelection([tableData[1], tableData[2]])"
        >Toggle selection status of second and third rows</el-button
        >
        <el-button @click="toggleSelection()">Clear selection</el-button>
    </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import { ElTable } from 'element-plus'
import { HttpManager } from "@/api/index"

interface User {
    date: string
    name: string
    address: string
}

const multipleTableRef = ref<InstanceType<typeof ElTable>>()
const multipleSelection = ref<User[]>([])
const toggleSelection = (rows?: User[]) => {
    if (rows) {
        rows.forEach((row) => {
            // TODO: improvement typing when refactor table
            // eslint-disable-next-line @typescript-eslint/ban-ts-comment
            // @ts-expect-error
            multipleTableRef.value!.toggleRowSelection(row, undefined)
        })
    } else {
        multipleTableRef.value!.clearSelection()
    }
}
const handleSelectionChange = (val: User[]) => {
    multipleSelection.value = val
}
const pageSize = ref(3); // 页数
const currentPage = ref(1); // 当前页

const tableData = ref([]);
getUserData(pageSize,currentPage);
//Person信息
async function getUserData(pageSize,currentPage) {
    tableData.value = [];
    const result = (await HttpManager.getPagesPeople(pageSize.value,currentPage.value)) as ResponseBody;
    tableData.value = result.data;
    console.log(result);
}
</script>
