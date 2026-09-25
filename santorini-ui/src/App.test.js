import { render, screen } from '@testing-library/react';
import App from './App';

test('offers both original game modes', () => {
  render(<App />);
  expect(screen.getByRole('button', { name: 'Play Without God Cards' })).toBeInTheDocument();
  expect(screen.getByRole('button', { name: 'Play With God Cards' })).toBeInTheDocument();
});
