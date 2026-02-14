<script setup lang="ts">
export interface Feature {
    icon?: string
    title: string
    details: string
    link?: string
}

defineProps<{
    features?: Feature[]
}>()
</script>

<template>
    <div class="KikoFeature">
        <div class="container">
            <div class="items">
                <div
                        v-for="(feature, index) in features"
                        :key="index"
                        class="item animate-feature"
                        :style="{ animationDelay: `${1.2 + index * 0.15}s` }"
                >
                    <article class="box">
                        <div v-if="feature.icon" class="icon" v-html="feature.icon"></div>
                        <h2 class="title" v-html="feature.title"></h2>
                        <p class="details" v-html="feature.details"></p>
                        <a v-if="feature.link" :href="feature.link" class="link">
                            En savoir plus ->
                        </a>
                    </article>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
@keyframes fadeInUp {
    from {
        opacity: 0;
        transform: translateY(40px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

.animate-feature {
    opacity: 0;
    animation: fadeInUp 0.7s cubic-bezier(0.16, 1, 0.3, 1) forwards;
}

.KikoFeature {
    position: relative;
    padding: 48px 24px;
}

@media (min-width: 640px) {
    .KikoFeature {
        padding: 64px 48px;
    }
}

@media (min-width: 960px) {
    .KikoFeature {
        padding: 96px 64px;
    }
}

.container {
    margin: 0 auto;
    max-width: 1152px;
}

.items {
    display: grid;
    grid-template-columns: 1fr;
    gap: 16px;
}

@media (min-width: 640px) {
    .items {
        grid-template-columns: repeat(2, 1fr);
        gap: 24px;
    }
}

@media (min-width: 960px) {
    .items {
        grid-template-columns: repeat(3, 1fr);
        gap: 32px;
    }
}

.item {
    transition: transform 0.3s ease;
}

.item:hover {
    transform: translateY(-4px);
}

.box {
    position: relative;
    height: 100%;
    padding: 28px;
    background-color: var(--vp-c-bg-soft);
    border: 1px solid var(--vp-c-divider);
    border-radius: 12px;
    transition: all 0.3s ease;
}

.box:hover {
    border-color: var(--vp-c-brand-1);
    box-shadow: 0 12px 32px rgba(0, 0, 0, 0.1);
}

.icon {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 48px;
    height: 48px;
    margin-bottom: 20px;
    font-size: 24px;
    background: var(--vp-c-brand-soft);
    border-radius: 10px;
    transition: transform 0.3s ease;
}

.box:hover .icon {
    transform: scale(1.1) rotate(5deg);
}

.title {
    margin: 0 0 12px;
    font-size: 20px;
    font-weight: 600;
    line-height: 28px;
    color: var(--vp-c-text-1);
}

.details {
    margin: 0;
    font-size: 15px;
    line-height: 18px;
    color: var(--vp-c-text-2);
}

.link {
    display: inline-block;
    margin-top: 16px;
    font-size: 14px;
    font-weight: 500;
    color: var(--vp-c-brand-1);
    text-decoration: none;
    transition: all 0.2s ease;
}

.link:hover {
    color: var(--vp-c-brand-2);
    transform: translateX(4px);
}

@media (prefers-reduced-motion: reduce) {
    .animate-feature {
        animation: none;
        opacity: 1;
    }

    .item:hover {
        transform: none;
    }

    .box:hover .icon {
        transform: scale(1.05);
    }
}
</style>