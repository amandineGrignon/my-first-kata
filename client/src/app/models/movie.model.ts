export class Movie {
  id: number = null;
  title = '';
  description = '';

  constructor(title: string, description: string) {
    this.title = title;
    this.description = description;
  }
}
