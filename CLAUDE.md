# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 프로젝트 개요

**Chirpy 테마** (v5.0.2)를 사용하는 Jekyll 기반 블로그입니다. https://hungryjayy.github.io 에 호스팅되며, 알고리즘, 데이터베이스, Docker, Redis, Kubernetes, Spring Boot 등 기술 주제를 다룹니다.

## 주요 명령어

### 개발 환경
```bash
# 의존성 설치
bundle

# 로컬 개발 서버 실행 (live reload 포함)
bundle exec jekyll s -H 0.0.0.0 -l
# 또는 편의 스크립트 사용
./tools/run.sh

# 프로덕션 빌드
JEKYLL_ENV=production bundle exec jekyll build

# JavaScript 자산 빌드
npm install
gulp          # JS 빌드
gulp dev      # 개발 모드 (압축 없음, 라이브 리빌드)
```

### 테스트
```bash
# html-proofer로 빌드된 사이트 테스트
bundle exec htmlproofer --disable-external --check-html --allow_hash_href _site
```

### 배포
```bash
# GitHub Pages에 배포 (GitHub Actions에서만 작동)
./tools/deploy.sh

# 드라이런 (배포 없이 빌드 및 테스트만)
./tools/deploy.sh --dry-run
```

## 아키텍처

### 콘텐츠 구조

**블로그 포스트**는 `_posts/` 하위 디렉토리에 주제별로 구성됩니다:
- 각 카테고리는 자체 폴더를 가집니다 (예: `Algorithm/`, `Redis/`, `Docker/`)
- 포스트 파일명 형식: `YYYY-MM-DD-제목.md`
- 모든 포스트는 한글로 작성됩니다

**포스트 frontmatter 형식**:
```yaml
---
layout: post
title: 포스트 제목
author:
  name: hungryjayy
  link: https://github.com/hungryjayy
tags: [태그1, 태그2]
categories: [카테고리, 서브카테고리]
date: 'YYYY-MM-DD'
extensions:
  preset: gfm
---
```

### 테마 아키텍처 (Chirpy)

**레이아웃** (`_layouts/`):
- `default.html` - 기본 템플릿
- `post.html` - 개별 블로그 포스트
- `home.html` - 포스트 목록이 있는 홈페이지
- `categories.html`, `tags.html` - 인덱스 페이지
- `compress.html` - HTML 압축

**Include 컴포넌트** (`_includes/`):
- 모듈형 컴포넌트: sidebar, topbar, footer, comments
- 기능 컴포넌트: `toc.html` (목차), `related-posts.html`, `post-sharing.html`
- 유틸리티 컴포넌트: `mode-toggle.html` (다크/라이트 테마), `mermaid.html`, `search-loader.html`

**JavaScript** (`_javascript/`):
- `commons/` - 공유 유틸리티
- `utils/` - 헬퍼 함수
- Gulp로 빌드 (`gulpfile.js/` 참조)

**데이터 파일** (`_data/`):
- `contact.yml` - 연락처 정보
- `share.yml` - 소셜 공유 설정
- `locales/` - 다국어 지원 파일

**탭** (`_tabs/`):
- 정적 페이지: About, Archives, Categories, Tags

### 사이트 설정

주요 설정 파일: `_config.yml`
- 사이트 메타데이터: title, tagline, description, URL
- 작성자 정보: 손주원 (hungryjayy)
- 소셜 링크: GitHub, LinkedIn, Instagram
- Google Analytics: G-62BFFHGZ29
- 테마 모드: auto (시스템 설정 따름)
- 페이지네이션: 페이지당 10개 포스트
- 타임존: Asia/Seoul

### 빌드 출력

- `_site/` - 생성된 정적 사이트 (gitignore됨)
- 빌드된 파일은 GitHub Actions를 통해 `gh-pages` 브랜치에 배포됨

## Jekyll 플러그인

런타임 의존성 (gemspec 기준):
- `jekyll-paginate` - 페이지네이션
- `jekyll-redirect-from` - URL 리다이렉트
- `jekyll-seo-tag` - SEO 최적화
- `jekyll-archives` - 카테고리/태그 아카이브
- `jekyll-sitemap` - 사이트맵 생성

커스텀 플러그인:
- `_plugins/posts-lastmod-hook.rb` - 포스트 수정 날짜 추적

## 개발 참고사항

- Ruby 버전: >= 2.4
- 포스트는 GitHub Flavored Markdown (GFM) 지원
- Rouge를 통한 코드 구문 강조
- 수식 표현 지원
- Mermaid 다이어그램 지원
- 다크/라이트 테마 토글 가능
- 사이트는 영어로 지역화되어 있으나 콘텐츠는 한글로 작성됨
