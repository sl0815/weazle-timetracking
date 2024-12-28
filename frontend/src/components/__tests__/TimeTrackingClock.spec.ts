import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import TimeTrackingClock from '../TimeTrackingClock.vue'

describe('TimeTrackingClock', () => {
  it('renders properly', async () => {
    const wrapper = mount(TimeTrackingClock)
    const today = new Date()
    const h = today.getHours()

    await wrapper.vm.$nextTick()
    expect(wrapper.text()).toContain(h)
  })
})
