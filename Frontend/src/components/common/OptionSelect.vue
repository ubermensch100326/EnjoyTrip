<script setup>
import { ref } from "vue";
defineProps({ optionList: Array });
const emit = defineEmits(["onKeySelect"]);

const gugun = ref("");

const onSelect = () => {
    console.log("Option Select : key.value => " + gugun.value);
    // console.log(key.value + "선택!!!");
    emit("onKeySelect", gugun.value);
};
</script>

<template>
    <h1>Select.vue</h1>
    <select v-model="gugun" class="form-select form-select-sm w-50" @change="onSelect">
        <option
            v-for="option in optionList"
            :key="option.value"
            :value="option.value"
            :disabled="option.value === '' ? true : false"
        >
            {{ option.text }}
        </option>
    </select>
</template>

<style scoped></style>
