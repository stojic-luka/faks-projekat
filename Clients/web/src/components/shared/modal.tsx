import { ReactNode, useEffect } from "react";
import { createPortal } from "react-dom";

interface Props {
  open: boolean;
  className?: string;
  children: ReactNode;
  onClose: () => void;
}
const Modal = ({ open, className, children, onClose }: Props) => {
  useEffect(() => {
    if (open) {
      document.body.style.overflow = "hidden";
    } else {
      document.body.style.removeProperty("overflow");
    }
  }, [open]);

  if (!open) return null;

  return createPortal(
    <>
      <div className="fixed bg-black/70 inset-0 z-[1000]" onClick={onClose} />
      <div className={`fixed flex flex-col top-1/2 left-1/2 translate-x-[-50%] translate-y-[-50%] z-[1001] ${className}`}>
        {children}
        <button className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 mx-auto mt-4" onClick={onClose}>
          Close Modal
        </button>
      </div>
    </>,
    document.getElementById("portal")!
  );
};

export default Modal;
