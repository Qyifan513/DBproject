<template>
    <el-table ref="multipleTableRef" :data="tableItemData" style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" />
        <el-table-column label="id" width="120">
            <template #default="scope">{{ scope.row.id }}</template>
        </el-table-column>
        <el-table-column property="name" label="项目名称" width="120" />

        <el-table-column property="content" label="内容" width="250" />
    </el-table>
    <div style="margin-top: 20px">
        <el-button @click="toggleSelection([tableItemData[1], tableItemData[2]])">Toggle selection status of second and
            third
            rows</el-button>
        <el-button @click="toggleSelection()">Clear selection</el-button>
    </div>
</template>

<script lang="ts" setup>
import { defineComponent, ref } from 'vue'
import { ElTable, selectGroupKey, valueEquals } from 'element-plus'
import { HttpManager } from "@/api/index";
import axios from 'axios';

interface Item {
    id: number
    name: string
    content: string
}

const multipleTableRef = ref<InstanceType<typeof ElTable>>()
const multipleSelection = ref<Item[]>([])
const toggleSelection = (rows?: Item[]) => {
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
const handleSelectionChange = (val: Item[]) => {
    multipleSelection.value = val
}

const pageSize = ref(3); // 页数
const currentPage = ref(1); // 当前页

const tableItemData = ref([]);
getItemData(pageSize,currentPage);

//Item信息
async function getItemData(pageSize,currentPage) {
    tableItemData.value = [];
    const result = (await HttpManager.getPagesItems(pageSize.value,currentPage.value)) as ResponseBody;
    tableItemData.value = result.data;
   // console.log(result);
}
console.log(tableItemData);

//Item信息
// async function getItemData() {
//     return new Promise(((resolve,reject)=>{
//         axios.get(`http://localhost:8080/admin/item/show?page=${currentPage.value}&size=${pageSize.value}`).then(
//             response => {
//                 resolve(response.data)
//                 console.log(response)
//             },
//             error => reject(error) 
//         )
//     }))
// }


</script>
