<script setup lang="ts">
import { ref, onMounted, watch } from 'vue';

const props = defineProps<{
    src?: string;
    placeholder?: string;
    alt?: string;
}>();

const imgSrc = ref<string>('');
const isLoading = ref(true);
const hasError = ref(false);

const CACHE_PREFIX = 'cached_image_';
const CACHE_EXPIRY = 7 * 24 * 60 * 60 * 1000;

interface CachedImageData {
    data: string;
    timestamp: number;
}

const getCachedImage = (url: string): string | null => {
    try {
        const key = CACHE_PREFIX + btoa(url);
        const cached = localStorage.getItem(key);
        if (cached) {
            const data: CachedImageData = JSON.parse(cached);
            if (Date.now() - data.timestamp < CACHE_EXPIRY) {
                return data.data;
            } else {
                localStorage.removeItem(key);
            }
        }
    } catch (e) {
        console.warn('Failed to get cached image:', e);
    }
    return null;
};

const setCachedImage = (url: string, dataUrl: string): void => {
    try {
        const key = CACHE_PREFIX + btoa(url);
        const data: CachedImageData = {
            data: dataUrl,
            timestamp: Date.now()
        };
        localStorage.setItem(key, JSON.stringify(data));
    } catch (e) {
        console.warn('Failed to cache image:', e);
    }
};

const loadImage = (url: string): void => {
    if (!url) {
        imgSrc.value = props.placeholder || '';
        isLoading.value = false;
        return;
    }

    const cached = getCachedImage(url);
    if (cached) {
        imgSrc.value = cached;
        isLoading.value = false;
        hasError.value = false;
        return;
    }

    const img = new Image();
    img.crossOrigin = 'anonymous';

    img.onload = () => {
        try {
            const canvas = document.createElement('canvas');
            canvas.width = img.naturalWidth;
            canvas.height = img.naturalHeight;
            const ctx = canvas.getContext('2d');
            if (ctx) {
                ctx.drawImage(img, 0, 0);
                const dataUrl = canvas.toDataURL('image/png');
                setCachedImage(url, dataUrl);
                imgSrc.value = dataUrl;
            } else {
                imgSrc.value = url;
            }
        } catch (e) {
            console.warn('Failed to cache image, using original URL:', e);
            imgSrc.value = url;
        }
        isLoading.value = false;
        hasError.value = false;
    };

    img.onerror = () => {
        hasError.value = true;
        isLoading.value = false;
        imgSrc.value = props.placeholder || '';
    };

    img.src = url;
};

onMounted(() => {
    if (props.src) {
        loadImage(props.src);
    } else if (props.placeholder) {
        imgSrc.value = props.placeholder;
        isLoading.value = false;
    }
});

watch(() => props.src, (newSrc) => {
    if (newSrc) {
        isLoading.value = true;
        hasError.value = false;
        loadImage(newSrc);
    }
});
</script>

<template>
    <div class="cached-image-wrapper">
        <img
            v-if="imgSrc"
            :src="imgSrc"
            :alt="alt || ''"
            :class="{ loading: isLoading, error: hasError }"
            v-bind="$attrs"
        />
        <div v-else-if="isLoading" class="loading-placeholder">
            <span>Loading...</span>
        </div>
        <div v-else-if="hasError" class="error-placeholder">
            <span>Failed to load image</span>
        </div>
    </div>
</template>

<style scoped>
.cached-image-wrapper {
    display: block;
    width: 100%;
    height: 100%;
}

img {
    display: block;
    max-width: 100%;
    height: auto;
    transition: opacity 0.3s ease;
}

img.loading {
    opacity: 0.5;
}

img.error {
    opacity: 0.3;
}

.loading-placeholder,
.error-placeholder {
    display: flex;
    align-items: center;
    justify-content: center;
    min-height: 200px;
    background: rgba(128, 128, 128, 0.1);
    border-radius: 8px;
}

.loading-placeholder span {
    color: var(--vp-c-text-2);
    font-size: 14px;
}

.error-placeholder span {
    color: var(--vp-c-danger);
    font-size: 14px;
}
</style>