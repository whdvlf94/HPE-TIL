import React from 'react';
import './App.css';
import BookList from './Container/Book-list';
import BookDetail from './Container/Book-detail'

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <BookList />
        <BookDetail />
      </header>
    </div>
  );
}

export default App;
