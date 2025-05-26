export interface User {
  id: string;
  username: string;
  email: string;
  roles: UserRoles[];
}

export const UserRoles = {
  USER: "user",
  ADMIN: "admin",
} as const;

export type UserRoles = (typeof UserRoles)[keyof typeof UserRoles];
