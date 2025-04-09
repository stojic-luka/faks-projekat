interface Props {
  value: boolean;
  onChange: () => void;
  disabled?: boolean;
}
const Switch = ({ value, onChange, disabled = false }: Props) => {
  return (
    <div
      className={`inline-block w-9 h-5 rounded-full relative cursor-pointer transition-color duration-300 ease-in-out ${
        value ? "bg-blue-500" : "bg-gray-400"
      }`}
      onClick={() => !disabled && onChange()}
    >
      <div className={`w-4 h-4 rounded-full bg-white absolute top-[2px] transition-left duration-300 ${value ? "left-[18px]" : "left-[2px]"}`} />
    </div>
  );
};

export default Switch;
