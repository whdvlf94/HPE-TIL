export function selectBook (book) {
    return {
        type: 'BOOK_SELECTED', //대문자 그리고 _ 사용
        payload: book

    }
}