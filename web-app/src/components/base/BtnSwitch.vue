<template>
  <!-- eslint-disable -->
  <div :class="'btn-switch' + (size ? ` btn-${size}` : '')">
    <span
      v-for="option in options"
      :key="option.value"
      :class="'btn-switch-option' + (option.value === value ? ' active' : '')"
      @click="onClickOption(option.value)"
    >
      {{option.label}}
    </span>
    <!-- <span class="btn-switch-option active">Log In</span>
    <span class="btn-switch-option">Sign Up</span> -->
  </div>
  <!-- eslint-enable -->
</template>

<script>
  export default {
    props: {
      size: {
        type: String,
        default: 'md',
        validator (value) {
          return ['sm', 'md', 'lg'].includes(value)
        },
      },
      options: {
        type: Array,
        required: true,
        validator (value) {
          if (!Array.isArray(value)) {
            return false
          }
          if (value.length !== 2) {
            return false
          }
          return value.filter(item => !item || item.value === undefined || item.label === undefined).length === 0
        },
      },
      value: {
        type: null,
      },
      change: {
        type: Function,
      },
    },
    methods: {
      onClickOption (value) {
        this.change(value)
      },
    },
  }
</script>

<style scoped>
.btn-switch {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 250px;
  height: 50px;
  background-color: #24CB431A;
  border-radius: 25px;
  box-shadow: 0px 0px 0px 2px #fafafa;
}

.btn-switch .btn-switch-option {
  display: block;
  white-space: nowrap;
  padding: 10px 15px;
  text-align: center;
  flex: 1;
  border-radius: 25px;
  cursor: pointer;
  color: #6A7C95;
  font-size: 1rem;
  user-select: none;
}

.btn-switch.btn-sm {
  width: 200px;
  height: 40px;
  border-radius: 22px;
  padding: 2px;
}
.btn-switch.btn-md {
  width: 250px;
  height: 40px;
}
.btn-switch.btn-lg {
  width: 300px;
  height: 40px;
}

.btn-switch .btn-switch-option.active {
  background: #24CB43;
  color: #fff;
}
</style>
