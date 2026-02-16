---
layout: page
navbar: true
sidebar: false
aside: false
outline: false
---

<script setup>
import KikoHero from '../.vitepress/theme/components/KikoHero.vue';
import KikoFeature from '../.vitepress/theme/components/KikoFeature.vue';

const features = [
    { title: "Feature A", details: "Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet" },
    { title: "Feature B", details: "Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet" },
    { title: "Feature C", details: "Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet" }
]
</script>

<KikoHero
name="KikoAPI docs"
text="A lightweight library for our plugins."
tagline="My great project tagline"
:image="{
src: 'https://raw.githubusercontent.com/KikoPlugins/.github/master/assets/logos/kikologo_transparent.png',
alt: 'KikoAPI Logo'
}"
:actions="[
{ theme: 'brand-button', text: 'Download', link: 'https://modrinth.com/plugins/kikoapi' },
{ theme: 'brand-button', text: 'Docs', link: '/markdown-examples' },
{ theme: 'alt', text: 'Test', link: '/api-examples' }
]"
/>

<KikoFeature :features="features" />