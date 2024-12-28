import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import Clock from '../Clock.vue'

describe('Clock', () => {
  it('renders properly', async () => {
    const wrapper = mount(Clock)
    const today = new Date()
    const h = today.getHours()

    await wrapper.vm.$nextTick()
    expect(wrapper.text()).toContain(h)
  })
})
