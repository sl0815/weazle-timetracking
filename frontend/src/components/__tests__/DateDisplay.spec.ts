import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import DateDisplay from '../DateDisplay.vue'

describe('DateDisplay', () => {
  it('renders properly', async () => {
    const wrapper = mount(DateDisplay)
    const today = new Date().toLocaleDateString()

    await wrapper.vm.$nextTick()
    expect(wrapper.text()).toContain(today)
  })
})
