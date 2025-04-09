export async function generateKey(): Promise<CryptoKey> {
  return window.crypto.subtle.generateKey({ name: "AES-GCM", length: 256 }, true, ["encrypt", "decrypt"]);
}

export async function encryptMessage(plainText: string, key: CryptoKey): Promise<{ cipherText: ArrayBuffer; iv: Uint8Array }> {
  const encoder = new TextEncoder();
  const data = encoder.encode(plainText);
  const iv = window.crypto.getRandomValues(new Uint8Array(12));
  const cipherText = await window.crypto.subtle.encrypt({ name: "AES-GCM", iv }, key, data);
  return { cipherText, iv };
}

export async function decryptMessage(cipherText: ArrayBuffer, key: CryptoKey, iv: Uint8Array): Promise<string> {
  const decrypted = await window.crypto.subtle.decrypt({ name: "AES-GCM", iv }, key, cipherText);
  return new TextDecoder().decode(decrypted);
}
