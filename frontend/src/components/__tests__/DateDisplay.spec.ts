import { describe, it, expect, beforeEach } from 'vitest'
import { mount } from '@vue/test-utils'
import DateDisplay from '../DateDisplay.vue'

describe('DateDisplay', () => {
  beforeEach(() => {
    Object.defineProperty(navigator, 'language', {
      value: 'de-DE',
      configurable: true,
    })
  })

  it('renders properly', async () => {
    const wrapper = mount(DateDisplay)
    const today = new Date()
    const userLocale = navigator.language
    const formattedDate = new Intl.DateTimeFormat(userLocale, {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
    }).format(today)

    await wrapper.vm.$nextTick()
    expect(wrapper.text()).toContain(formattedDate)
  })
})
